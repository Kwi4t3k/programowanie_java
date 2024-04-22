import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //krok 1 test
//        Double[] lista = new Double[0];
//        NonFoodProduct nonFoodProduct = new NonFoodProduct("produkt", lista);
//        nonFoodProduct.getPrice(2020, 8);

//        FoodProduct foodProduct = FoodProduct.fromCsv(Path.of("kolos1_2022\\kolokwium1_2022\\kolokwium1_2022\\data\\data\\food\\buraki.csv"));
//        System.out.println(foodProduct.getPrice(2011, 1, "LUBELSKIE"));  // wybieranie danej ceny
//        System.out.println(foodProduct.getPrice(2010, 1)); // średnia

        Product.addProducts(FoodProduct::fromCsv, Path.of("kolos1_2022\\kolokwium1_2022\\kolokwium1_2022\\data\\data\\food"));
        System.out.println(Product.getProductList());
    }
}