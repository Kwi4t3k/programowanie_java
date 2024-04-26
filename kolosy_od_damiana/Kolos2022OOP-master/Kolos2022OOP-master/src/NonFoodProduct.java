import java.io.IOException;  // Importowanie klasy IOException do obsługi wyjątków wejścia/wyjścia
import java.nio.file.Path;  // Importowanie klasy Path do obsługi ścieżek plików
import java.util.Arrays;  // Importowanie klasy Arrays do obsługi tablic
import java.util.Scanner;  // Importowanie klasy Scanner do odczytu danych

// Definicja klasy NonFoodProduct, która dziedziczy po klasie Product
public class NonFoodProduct extends Product {
    Double[] prices;  // Tablica cen

    // Prywatny konstruktor klasy NonFoodProduct
    private NonFoodProduct(String name, Double[] prices) {
        super(name);  // Wywołanie konstruktora klasy nadrzędnej
        this.prices = prices;  // Przypisanie wartości do pola prices
    }

    // Metoda tworząca obiekt klasy NonFoodProduct na podstawie danych z pliku CSV
    public static NonFoodProduct fromCsv(Path path) {
        String name;  // Nazwa produktu
        Double[] prices;  // Tablica cen

        try {
            Scanner scanner = new Scanner(path);  // Utworzenie obiektu Scanner do odczytu pliku
            name = scanner.nextLine(); // Odczytanie pierwszej linii i zapisanie jej jako nazwa
            scanner.nextLine();  // Pominięcie drugiej linii z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // Odczytanie kolejnej linii i podział jej na tablicę
                    .map(value -> value.replace(",",".")) // Zamiana polskiego znaku ułamka dziesiętnego - przecinka na kropkę
                    .map(Double::valueOf) // Konwersja stringa na double
                    .toArray(Double[]::new); // Dodanie elementów do nowo utworzonej tablicy

            scanner.close();  // Zamknięcie skanera

            return new NonFoodProduct(name, prices);  // Zwrócenie nowego obiektu klasy NonFoodProduct

        } catch (IOException e) {
            throw new RuntimeException(e);  // Rzucenie wyjątku w przypadku błędu wejścia/wyjścia
        }
    }

    // Metoda zwracająca cenę produktu w danym roku i miesiącu
    @Override
    public double getPrice(int year, int month) {
        int numberOfMonths = (2022 - 2010)*12 + 3; // Obliczenie liczby miesięcy od początku 2010 roku do marca 2022 roku
        //rok końcowy - rok początkowy + 3 miesiące do marca
        int lookedDateIndex = (year - 2010)*12 - 1 + month;  // Obliczenie indeksu szukanej daty
        if((lookedDateIndex >= numberOfMonths || lookedDateIndex < 0) || month > 12) {  // Jeśli indeks jest poza zakresem lub miesiąc jest większy niż 12
            throw new IndexOutOfBoundsException();  // Rzucenie wyjątku
        }
        return prices[lookedDateIndex];  // Zwrócenie ceny produktu na daną datę
    }
}
