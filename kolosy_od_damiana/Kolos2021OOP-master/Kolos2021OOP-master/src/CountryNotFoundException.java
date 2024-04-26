// Definicja klasy CountryNotFoundException, która dziedziczy po klasie Exception
public class CountryNotFoundException extends Exception {
    // Konstruktor klasy CountryNotFoundException
    public CountryNotFoundException(String name) {
        super("Country " + name + " not found");  // Wywołanie konstruktora klasy nadrzędnej z komunikatem o błędzie
    }
}
