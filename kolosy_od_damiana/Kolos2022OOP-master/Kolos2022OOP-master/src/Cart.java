import java.util.ArrayList;  // Importowanie klasy ArrayList do obsługi list
import java.util.List;  // Importowanie interfejsu List do obsługi list

// Definicja klasy Cart
public class Cart {
    List<Product> products = new ArrayList<Product>();  // Lista produktów w koszyku

    // Metoda dodająca produkt do koszyka
    public void addProduct(Product product, int amount) {
        for(int i = 0 ; i < amount ; i++) {  // Iteracja przez ilość produktów do dodania
            products.add(product);  // Dodanie produktu do koszyka
        }
    }

    // Metoda zwracająca cenę wszystkich produktów w koszyku w danym roku i miesiącu
    public double getPrice(int year, int month) {
        double price = 0;  // Inicjalizacja zmiennej przechowującej cenę
        for(Product product : products) {  // Iteracja przez produkty w koszyku
            price += product.getPrice(year, month);  // Dodanie ceny produktu do zmiennej przechowującej cenę
        }
        return price;  // Zwrócenie ceny
    }

    // Metoda zwracająca inflację między dwoma datami
    public double getInflation(int year1, int month1, int year2, int month2) {
        if(year1 * 12 + month1 > year2 * 12 + month2) {  // Jeśli pierwsza data jest późniejsza niż druga
            return -1;  // Zwrócenie -1
        }
        double price1 = getPrice(year1, month1);  // Pobranie ceny na pierwszą datę
        double price2 = getPrice(year2, month2);  // Pobranie ceny na drugą datę
        int months = (year2 * 12 + month2) - (year1 * 12 + month1);  // Obliczenie liczby miesięcy między datami
        return (price2 - price1) / price1 * 100 / months * 12;  // Obliczenie i zwrócenie inflacji
    }
}
