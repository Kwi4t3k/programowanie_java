import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class FoodProduct extends Product {
    // Mapa przechowująca ceny produktów
    Map<String, Double[]> prices = new HashMap<>();

    // Prywatny konstruktor klasy FoodProduct
    private FoodProduct(String name, Map<String, Double[]> prices) {
        super(name); // Wywołanie konstruktora klasy nadrzędnej
        this.prices = prices; // Przypisanie wartości do zmiennej instancji
    }

    // Metoda statyczna tworząca obiekt klasy FoodProduct na podstawie pliku CSV
    public static FoodProduct fromCsv(Path path) {
        String name;
        Map<String, Double[]> prices = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
            name = br.readLine(); // Czytanie nazwy produktu
            String line = br.readLine();
            // Czytanie linii pliku CSV i przypisywanie wartości do mapy
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(";", 2);
                Double[] price = Arrays.stream(tokens[1].split(";")).map(value -> value.replaceAll(",", ".")).map(Double::parseDouble).toArray(Double[]::new);
                prices.put(tokens[0], price);
            }
            // Zwracanie nowego obiektu klasy FoodProduct
            return new FoodProduct(name, prices);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Metoda zwracająca cenę produktu dla danego roku, miesiąca i prowincji
    public double getPrice(int year, int month, String province) {
        int numberOfMonths = (2022 - 2010)*12 + 3; //rok końcowy - rok początkowy + 3 miesiące do marca
        int lookedDateIndex = (year - 2010)*12 - 1 + month;
        if((lookedDateIndex >= numberOfMonths || lookedDateIndex < 0) || month > 12) {
            throw new IndexOutOfBoundsException();
        }
        if(!prices.containsKey(province)) {
            throw new IndexOutOfBoundsException();
        }
        Double[] value = prices.get(province);
        System.out.println(lookedDateIndex);
        return value[lookedDateIndex];
    }

    // Metoda zwracająca średnią cenę produktu dla danego roku i miesiąca
    @Override
    public double getPrice(int year, int month) {
        int numberOfMonths = (2022 - 2010)*12 + 3; //rok końcowy - rok początkowy + 3 miesiące do marca
        int lookedDateIndex = (year - 2010)*12 - 1 + month;
        if((lookedDateIndex >= numberOfMonths || lookedDateIndex < 0) || month > 12) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        double sum = 0;
        // Obliczanie średniej ceny
        for(Double[] arr : prices.values()) {
            sum += arr[lookedDateIndex];
            counter++;
        }
        return sum/counter;
    }
}
