import java.util.List;

public class AmbigiousProductException extends Exception {
    // Lista produktów, które są niejednoznaczne
    private List<String> productList;

    // Konstruktor klasy AmbigiousProductException
    AmbigiousProductException(List<String> list) {
        super("Products are ambiguous"); // Wywołanie konstruktora klasy nadrzędnej
        this.productList = list; // Przypisanie wartości do zmiennej instancji
    }

    // Nadpisanie metody getMessage klasy Exception
    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getMessage()).append(": "); // Dodanie wiadomości z klasy nadrzędnej
        // Dodanie nazw produktów do wiadomości
        for (String product : productList) {
            sb.append(product).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // Usunięcie ostatniego przecinka i spacji
        return sb.toString(); // Zwrócenie gotowej wiadomości
    }
}
