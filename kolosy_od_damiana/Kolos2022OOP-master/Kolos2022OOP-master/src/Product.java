import java.io.File;  // Importowanie klasy File do obsługi plików
import java.nio.file.Path;  // Importowanie klasy Path do obsługi ścieżek plików
import java.util.ArrayList;  // Importowanie klasy ArrayList do obsługi list
import java.util.List;  // Importowanie interfejsu List do obsługi list
import java.util.function.Function;  // Importowanie interfejsu Function do obsługi funkcji

// Definicja abstrakcyjnej klasy Product
public abstract class Product {
    private String name;  // Nazwa produktu
    private static List<Product> products = new ArrayList<>();  // Lista produktów

    // Konstruktor klasy Product
    public Product(String name) {
        this.name = name;  // Przypisanie wartości do pola name
    }

    // Metoda zwracająca nazwę produktu
    public String getName() {
        return name;
    }

    // Metoda zwracająca listę produktów
    public static List<Product> getProducts() {
        return products;
    }

    // Metoda zwracająca produkt o podanym prefiksie
    public static Product getProducts (String prefix) throws AmbigiousProductException {
        List<Product> result = new ArrayList<>();  // Lista wyników
        List<String> names = new ArrayList<>();  // Lista nazw
        for (Product product : products) {  // Iteracja przez produkty
            if (product.name.startsWith(prefix)) {  // Jeśli nazwa produktu zaczyna się od prefiksu
                result.add(product);  // Dodanie produktu do listy wyników
                names.add(product.name);  // Dodanie nazwy produktu do listy nazw
            }
        }
        if (result.size() == 0) {  // Jeśli lista wyników jest pusta
            throw new IndexOutOfBoundsException();  // Rzucenie wyjątku
        } else if (result.size() == 1) {  // Jeśli lista wyników zawiera jeden element
            return result.get(0);  // Zwrócenie tego elementu
        } else {  // Jeśli lista wyników zawiera więcej niż jeden element
            throw new AmbigiousProductException(names);  // Rzucenie wyjątku
        }
    }

    // Abstrakcyjna metoda zwracająca cenę produktu w danym roku i miesiącu
    public abstract double getPrice(int year, int month);

    // Metoda usuwająca wszystkie produkty z listy
    public static void clearProducts() {
        products.clear();
    }

    // Metoda dodająca produkt do listy na podstawie danych z pliku CSV
    public static void addProduct(Function<Path, Product> fromCsv, Path path) {
        File dir = new File(path.toString());  // Utworzenie obiektu File dla ścieżki
        File[] fileList = dir.listFiles();  // Pobranie listy plików w katalogu
        assert fileList != null;  // Sprawdzenie, czy lista plików nie jest pusta
        for (File file : fileList) {  // Iteracja przez pliki
            if (file.isFile()) {  // Jeśli obiekt jest plikiem
                products.add(fromCsv.apply(file.toPath()));  // Dodanie produktu do listy
            }
        }
    }
}
