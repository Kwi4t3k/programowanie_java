import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

// Klasa reprezentująca kraj bez prowincji
public class CountryWithoutProvinces extends Country {

    // Mapy przechowujące daty i liczbę zgonów oraz infekcji
    Map<LocalDate, Integer> deaths = new HashMap<LocalDate, Integer>();
    Map<LocalDate, Integer> infections = new HashMap<>();

    // Konstruktor klasy
    public CountryWithoutProvinces(String name) {
        super(name);
    }

    // Metoda dodająca statystyki na dany dzień
    public void addDailyStatistics(LocalDate date, Integer deaths, Integer infections) {
        this.deaths.put(date, deaths);
        this.infections.put(date, infections);
    }

    // Metoda wczytująca informacje z pliku
    public static CountryWithoutProvinces loadInfoFromFile(String path, int index, String name) {
        try {
            // Tworzenie nowego obiektu klasy
            CountryWithoutProvinces result = new CountryWithoutProvinces(name);
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            line = br.readLine();
            final int temp = index;
            // Wczytywanie linii z pliku
            while((line = br.readLine()) != null) {
                String[] split = line.split(";");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
                LocalDate date = LocalDate.parse(split[0], formatter);
                Integer deaths = Integer.parseInt(split[temp]);
                Integer infections = Integer.parseInt(split[temp]);
                // Dodawanie statystyk do obiektu
                result.addDailyStatistics(date, deaths, infections);
            }
            br.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda zwracająca liczbę potwierdzonych przypadków na daną datę
    @Override
    public Integer getConfirmedCases(LocalDate date) {
        return infections.get(date);
    }

    // Metoda zwracająca liczbę zgonów na daną datę
    @Override
    public Integer getDeaths(LocalDate date) {
        return deaths.get(date);
    }

    // Metoda zwracająca reprezentację obiektu jako ciąg znaków
    @Override
    public String toString() {
        return super.name + "\n Deaths: " + this.deaths.toString() + "\n Infections: " + this.infections.toString();
    }
}
