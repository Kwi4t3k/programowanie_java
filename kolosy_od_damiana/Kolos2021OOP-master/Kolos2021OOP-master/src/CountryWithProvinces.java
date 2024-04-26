import java.time.LocalDate;  // Importowanie klasy LocalDate do obsługi dat

// Definicja klasy CountryWithProvinces, która dziedziczy po klasie Country
public class CountryWithProvinces extends Country{
    private Country[] provinces;  // Tablica prowincji

    // Konstruktor klasy CountryWithProvinces
    public CountryWithProvinces(String name, Country[] provinces) {
        super(name);  // Wywołanie konstruktora klasy nadrzędnej
        this.provinces = provinces;  // Przypisanie wartości do pola provinces
    }

    // Metoda zwracająca liczbę potwierdzonych przypadków na daną datę
    @Override
    public Integer getConfirmedCases(LocalDate date) {
        int sum = 0;  // Inicjalizacja licznika przypadków
        for(Country province : provinces) {  // Iteracja przez prowincje
            sum += province.getConfirmedCases(date);  // Dodanie liczby przypadków w prowincji do licznika
        }
        return sum;  // Zwrócenie całkowitej liczby przypadków
    }

    // Metoda zwracająca liczbę zgonów na daną datę
    @Override
    public Integer getDeaths(LocalDate date) {
        int sum = 0;  // Inicjalizacja licznika zgonów
        for(Country province : provinces) {  // Iteracja przez prowincje
            sum += province.getDeaths(date);  // Dodanie liczby zgonów w prowincji do licznika
        }
        return sum;  // Zwrócenie całkowitej liczby zgonów
    }

    // Metoda zwracająca reprezentację obiektu jako łańcuch znaków
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.name);  // Utworzenie obiektu StringBuilder z nazwą kraju
        for(Country province : provinces) {  // Iteracja przez prowincje
            sb.append(province.toString() + "\n");  // Dodanie reprezentacji prowincji do łańcucha znaków
        }
        return sb.toString();  // Zwrócenie łańcucha znaków
    }
}
