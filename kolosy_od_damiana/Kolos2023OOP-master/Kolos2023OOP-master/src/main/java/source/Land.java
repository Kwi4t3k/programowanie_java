package source;

import java.util.ArrayList;
import java.util.List;

public class Land extends Polygon{

    // Lista miast na danym lądzie
    private List<City> cities = new ArrayList<City>();

    // Metoda dodająca miasto do listy miast na danym lądzie
    public void addCity(City c){
        try {
            cities.add(c); // Dodajemy miasto do listy
            c.setPort(this); // Ustawiamy port dla miasta
            centerOnLand(c); // Sprawdzamy, czy miasto jest na lądzie
        } catch(RuntimeException e) {
            // Obsługujemy wyjątek, jeśli miasto nie jest na lądzie
            System.out.println(e.getMessage());
        }
    }

    // Konstruktor klasy Land
    public Land(List<Point> points) {
        super(points); // Wywołanie konstruktora klasy nadrzędnej
    }

    // Metoda sprawdzająca, czy miasto jest na lądzie
    private void centerOnLand(City c) throws RuntimeException {
        if(!this.inside(c.center)) {
            // Jeśli miasto nie jest na lądzie, rzucamy wyjątek
            throw new RuntimeException(c.getName());
        }
    }
}
