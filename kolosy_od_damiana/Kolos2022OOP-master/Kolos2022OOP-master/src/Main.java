import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// WSKAZÓWKA Aby <b>Uruchomić</b> kod, naciśnij <shortcut actionId="Run"/> lub
// kliknij ikonę <icon src="AllIcons.Actions.Execute"/> w marginesie.
public class Main {
    // Ścieżki do plików z danymi o produktach
    public static final String nonFoodPath = "/home/damian/GitHub/Kolos2021OOp/data/nonfood";
    public static final String foodPath = "/home/damian/GitHub/Kolos2021OOp/data/food";

    public static void main(String[] args) {
        // Dodajemy produkty do naszej listy produktów


//        Scanner scanner = new Scanner(System.in);
//        String lineName = scanner.nextLine();
//        String path = foodPath + "/" + lineName + ".csv";
//        FoodProduct p = FoodProduct.fromCsv(Paths.get(path));
//        System.out.println(p.getName());
//        System.out.println(p.getPrice(2010, 1));
//        System.out.println(p.getPrice(2010, 1, "LUBELSKIE"));
        Product.addProduct(FoodProduct::fromCsv, Paths.get(foodPath));
        Product.addProduct(NonFoodProduct::fromCsv, Paths.get(nonFoodPath));
//        try {
//            System.out.println(Product.getProducts("J").getName());
//        } catch (AmbigiousProductException e) {
//            e.printStackTrace();
//        }


        // Tworzymy nowy koszyk
        Cart cart = new Cart();
        try {
            // Dodajemy produkty do koszyka
            cart.addProduct(Product.getProducts("Jaja"), 5);
            cart.addProduct(Product.getProducts("Bu"), 2);
            cart.addProduct(Product.getProducts("Garni"), 1);
        } catch (AmbigiousProductException e) {
            // Obsługujemy wyjątek, jeśli produkt o danej nazwie nie jest jednoznaczny
            e.printStackTrace();
        }
        // Wyświetlamy nazwy produktów w koszyku
        for(Product product : cart.products) {
            System.out.println(product.getName());
        }

        // Obliczamy i wyświetlamy cenę koszyka dla danego roku i kwartału
        double price = cart.getPrice(2010, 2);
        System.out.println(price);

        // Obliczamy i wyświetlamy inflację dla danego okresu
        double inflation = cart.getInflation(2010, 2, 2012, 2);
        System.out.println(inflation);
    }
}