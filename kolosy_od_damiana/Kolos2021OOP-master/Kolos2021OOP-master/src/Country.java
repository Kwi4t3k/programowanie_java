import java.io.*;  // Importowanie klas do obsługi wejścia/wyjścia
import java.nio.file.Path;  // Importowanie klasy Path do obsługi ścieżek plików
import java.nio.file.Paths;  // Importowanie klasy Paths do obsługi ścieżek plików
import java.time.LocalDate;  // Importowanie klasy LocalDate do obsługi dat
import java.time.format.DateTimeFormatter;  // Importowanie klasy DateTimeFormatter do formatowania dat
import java.util.*;  // Importowanie wszystkich klas z pakietu java.util
import java.util.stream.Collectors;  // Importowanie klasy Collectors do obsługi operacji na strumieniach

// Definicja abstrakcyjnej klasy Country
public abstract class Country {
    public final String name;  // Nazwa kraju
    static private Path deathsPath;  // Ścieżka do pliku z danymi o zgonach
    static private Path confirmedCasesPath;  // Ścieżka do pliku z danymi o potwierdzonych przypadkach

    // Konstruktor klasy Country
    public Country(String name) {
        this.name = name;  // Przypisanie wartości do pola name
    }

    // Metoda zwracająca nazwę kraju
    public String getName() {
        return name;
    }

    // Metoda ustawiająca ścieżki do plików z danymi
    static void setFiles(String deaths, String confirmed) throws FileNotFoundException {
        deathsPath = Paths.get(deaths);  // Ustawienie ścieżki do pliku z danymi o zgonach
        confirmedCasesPath = Paths.get(confirmed);  // Ustawienie ścieżki do pliku z danymi o potwierdzonych przypadkach
        File D = new File(deaths);  // Utworzenie obiektu pliku dla pliku z danymi o zgonach
        File C = new File(confirmed);  // Utworzenie obiektu pliku dla pliku z danymi o potwierdzonych przypadkach
        if (!D.exists() || !D.canRead()) {  // Sprawdzenie, czy plik istnieje i czy jest do odczytu
            throw new FileNotFoundException(deaths);  // Jeśli nie, rzucenie wyjątku
        }
        if (!C.exists() || !C.canRead()) {  // Sprawdzenie, czy plik istnieje i czy jest do odczytu
            throw new FileNotFoundException(confirmed);  // Jeśli nie, rzucenie wyjątku
        }
    }

    // Metoda tworząca obiekt klasy Country na podstawie danych z pliku CSV
    public static Country fromCsv(String name) {
        try (BufferedReader bf = new BufferedReader(new FileReader(deathsPath.toFile()))) {
            // Sprawdzenie, czy kraj ma prowincje i znalezienie kolumn
            String line;
            boolean hasProvincess = true;
            line = bf.readLine();
            CountryColumns cc = getCountryColumns(line, name);  // Pobranie kolumn kraju
            if(cc.columnCount == 0) {
                hasProvincess = false;  // Jeśli kraj nie ma prowincji, ustawienie flagi na false
            }
            // Dla prowincji
            line = bf.readLine();
            if(hasProvincess) {
                List<Country> provinces = new ArrayList<>();  // Lista prowincji
                String[] split = line.split(";");  // Podział linii na elementy
                for(int i = cc.firstColumnIndex; i < cc.firstColumnIndex + cc.columnCount; i++) {
                    CountryWithoutProvinces temp = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), i, split[i]);  // Wczytanie informacji o prowincji
                    provinces.add(temp);  // Dodanie prowincji do listy
                }
                return new CountryWithProvinces(name, provinces.toArray(new Country[0]));  // Zwrócenie kraju z prowincjami
            } else {
                CountryWithoutProvinces result = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), cc.firstColumnIndex, name);  // Wczytanie informacji o kraju bez prowincji
                return result;  // Zwrócenie kraju bez prowincji
            }
        } catch (IOException e) {
            throw new RuntimeException(e);  // Rzucenie wyjątku w przypadku błędu wejścia/wyjścia
        } catch (CountryNotFoundException e) {
            System.out.println(e.getMessage());  // Wyświetlenie komunikatu o błędzie
        }
        return null;  // Zwrócenie null, jeśli nie udało się utworzyć kraju
    }

    // Klasa pomocnicza do przechowywania informacji o kolumnach kraju
    private static class CountryColumns{
        public final int firstColumnIndex, columnCount;  // Indeks pierwszej kolumny i liczba kolumn
        public CountryColumns(int firstColumnIndex, int columnIndex) {
            this.firstColumnIndex = firstColumnIndex;  // Przypisanie wartości do pola firstColumnIndex
            this.columnCount = columnIndex;  // Przypisanie wartości do pola columnCount
        }
    }

    // Metoda zwracająca informacje o kolumnach kraju
    private static CountryColumns getCountryColumns(String firstCsvLine, String name) throws CountryNotFoundException {
        int startIndex, endIndex;
        List<String> stringList = new ArrayList<>(Arrays.asList(firstCsvLine.split(";")));  // Podział linii na elementy
        startIndex = stringList.indexOf(name);  // Znalezienie indeksu początkowego
        if(startIndex == -1) {
            throw new CountryNotFoundException(name);  // Jeśli nie znaleziono kraju, rzucenie wyjątku
        }
        endIndex = stringList.lastIndexOf(name);  // Znalezienie indeksu końcowego
        return new CountryColumns(startIndex, endIndex - startIndex);  // Zwrócenie informacji o kolumnach
    }

    // Metoda tworząca tablicę obiektów klasy Country na podstawie danych z pliku CSV
    public static Country[] fromCsv(String[] names) {
        List<Country> result = new ArrayList<>();  // Lista krajów
        for(String name : names) {
            try (BufferedReader bf = new BufferedReader(new FileReader(deathsPath.toFile()))) {
                // Sprawdzenie, czy kraj ma prowincje i znalezienie kolumn
                String line;
                boolean hasProvincess = true;
                line = bf.readLine();
                CountryColumns cc = getCountryColumns(line, name);  // Pobranie kolumn kraju
                if(cc.columnCount == 0) {
                    hasProvincess = false;  // Jeśli kraj nie ma prowincji, ustawienie flagi na false
                }
                // Dla prowincji
                line = bf.readLine();
                if(hasProvincess) {
                    List<Country> provinces = new ArrayList<>();  // Lista prowincji
                    String[] split = line.split(";");  // Podział linii na elementy
                    for(int i = cc.firstColumnIndex; i < cc.firstColumnIndex + cc.columnCount; i++) {
                        CountryWithoutProvinces temp = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), i, split[i]);  // Wczytanie informacji o prowincji
                        provinces.add(temp);  // Dodanie prowincji do listy
                    }
                    result.add(new CountryWithProvinces(name, provinces.toArray(new Country[0])));  // Dodanie kraju z prowincjami do listy
                } else {
                    CountryWithoutProvinces cwp = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), cc.firstColumnIndex, name);  // Wczytanie informacji o kraju bez prowincji
                    result.add(cwp);  // Dodanie kraju bez prowincji do listy
                }
            } catch (IOException e) {
                throw new RuntimeException(e);  // Rzucenie wyjątku w przypadku błędu wejścia/wyjścia
            } catch (CountryNotFoundException e) {
                System.out.println(e.getMessage());  // Wyświetlenie komunikatu o błędzie
            }
        }
        return result.toArray(new Country[0]);  // Zwrócenie tablicy krajów
    }

    // Abstrakcyjna metoda zwracająca liczbę potwierdzonych przypadków na daną datę
    public abstract Integer getConfirmedCases(LocalDate date);

    // Abstrakcyjna metoda zwracająca liczbę zgonów na daną datę
    public abstract Integer getDeaths(LocalDate date);

    // Metoda sortująca listę krajów według liczby zgonów w danym okresie
    public static List<Country> sortByDeaths(List<Country> list, LocalDate startDate, LocalDate endDate) {
        return list.stream()  // Tworzenie strumienia z listy krajów
                .sorted(Comparator.comparingInt(c -> getTotalDeaths(c, startDate, endDate)))  // Sortowanie strumienia według liczby zgonów
                .collect(Collectors.toList());  // Zbieranie posortowanego strumienia do listy
    }

    // Metoda zwracająca całkowitą liczbę zgonów w danym kraju i okresie
    private static int getTotalDeaths(Country country, LocalDate startDate, LocalDate endDate) {
        int totalDeaths = 0;  // Inicjalizacja licznika zgonów
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {  // Iteracja przez daty od startDate do endDate
            int deaths = country.getDeaths(date);  // Pobranie liczby zgonów na daną datę
            if (deaths > 0) {  // Jeśli liczba zgonów jest większa od 0
                totalDeaths += deaths;  // Dodanie liczby zgonów do licznika
            }
        }
        return totalDeaths;  // Zwrócenie całkowitej liczby zgonów
    }

    // Metoda zwracająca mapę dat i całkowitej liczby zgonów na daną datę
    private static Map<LocalDate, Integer> totalPerDate(Path path) {
        Map<LocalDate, Integer> map = new HashMap<>();  // Inicjalizacja mapy
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path.toString()));  // Tworzenie obiektu BufferedReader do odczytu pliku
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");  // Tworzenie formatera daty
            String line = bf.readLine();  // Odczytanie pierwszej linii pliku
            line = bf.readLine();  // Odczytanie drugiej linii pliku
            while ((line = bf.readLine()) != null) {  // Iteracja przez kolejne linie pliku
                List<String> split = Arrays.asList(line.split(";"));  // Podział linii na elementy
                Integer sum = split.stream().skip(1).mapToInt(Integer::parseInt).sum();  // Sumowanie elementów linii
                map.put(LocalDate.parse(split.get(0), formatter), sum);  // Dodanie daty i sumy do mapy
            }
            bf.close();  // Zamknięcie BufferedReadera
            return map;  // Zwrócenie mapy
        } catch (IOException e) {
            throw new RuntimeException(e);  // Rzucenie wyjątku w przypadku błędu wejścia/wyjścia
        }
    }

    // Metoda zapisująca dane do pliku
    public static void saveToDataFile(Path path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString()));  // Tworzenie obiektu BufferedWriter do zapisu do pliku
            Map<LocalDate, Integer> mapOfDeaths = totalPerDate(deathsPath);  // Pobranie mapy dat i zgonów
            Map<LocalDate, Integer> mapOfInfections = totalPerDate(confirmedCasesPath);  // Pobranie mapy dat i infekcji

            for (Map.Entry<LocalDate, Integer> entry : mapOfDeaths.entrySet()) {  // Iteracja przez wpisy mapy
                bw.write(entry.getKey().format(DateTimeFormatter.ofPattern("d.M.yy")));  // Zapisanie daty do pliku
                bw.write("\t");  // Zapisanie tabulatora do pliku
                bw.write(entry.getValue().toString());  // Zapisanie liczby zgonów do pliku
                bw.write("\t");  // Zapisanie tabulatora do pliku
                bw.write(mapOfInfections.getOrDefault(entry.getKey(), 0).toString()); // Zapisanie liczby infekcji do pliku
                bw.newLine();  // Zapisanie nowej linii do pliku
            }

            bw.close();  // Zamknięcie BufferedWritera
        } catch (IOException e) {
            throw new RuntimeException(e);  // Rzucenie wyjątku w przypadku błędu wejścia/wyjścia
        }
    }
}