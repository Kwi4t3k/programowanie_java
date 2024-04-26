package source;

import java.util.ArrayList;  // Importowanie klasy ArrayList do obsługi list
import java.util.List;  // Importowanie interfejsu List do obsługi list
import java.util.Set;  // Importowanie interfejsu Set do obsługi zbiorów

// Definicja klasy City, która dziedziczy po klasie Polygon
public class City extends Polygon{
    public final Point center;  // Centrum miasta
    private String name;  // Nazwa miasta
    private boolean isPort = false;  // Flaga informująca, czy miasto jest portem
    Set<Resource.Type> resources;  // Zbiór typów zasobów dostępnych w mieście

    // Konstruktor klasy City
    public City(Point center, String name, double length) {
        super(calculateCityCorners(center, length));  // Wywołanie konstruktora klasy nadrzędnej
        this.center = center;  // Przypisanie wartości do pola center
        this.name = name;  // Przypisanie wartości do pola name
    }

    // Metoda zwracająca zbiór typów zasobów
    public Set<Resource.Type> getResources() {
        return resources;
    }

    // Metoda zwracająca nazwę miasta
    public String getName() {
        return name;
    }

    // Metoda obliczająca wierzchołki miasta
    private static List<Point> calculateCityCorners(Point center, double length) {
        List<Point> corners = new ArrayList<Point>();  // Lista wierzchołków
        double halfLength = length / 2;  // Połowa długości
        // Dodanie wierzchołków do listy
        corners.add(new Point(center.x - halfLength, center.y - halfLength));
        corners.add(new Point(center.x + halfLength, center.y - halfLength));
        corners.add(new Point(center.x - halfLength, center.y + halfLength));
        corners.add(new Point(center.x + halfLength, center.y + halfLength));
        return corners;  // Zwrócenie listy wierzchołków
    }

    // Metoda ustawiająca flagę isPort na true, jeśli miasto jest portem
    public void setPort(Land land) {
        for(Point point : land.getPoints()) {  // Iteracja przez punkty terenu
            if(!land.inside(point)) {  // Jeśli punkt nie jest wewnątrz terenu
                isPort = true;  // Ustawienie flagi isPort na true
            }
        }
    }

    // Metoda dodająca zasoby w zasięgu do zbioru zasobów
    void addResourcesInRange(List<Resource> resources, double range) {
        for(Resource resource : resources) {  // Iteracja przez zasoby
            // Jeśli zasób jest w zasięgu
            if(range <= Math.hypot(this.center.x - resource.localization.x, this.center.y - resource.localization.y)) {
                // Jeśli typ zasobu nie jest rybą
                if(!resource.type.equals(Resource.Type.Fish)) {
                    this.resources.add(resource.type);  // Dodanie typu zasobu do zbioru
                } else {
                    // Jeśli miasto jest portem
                    if(this.isPort) {
                        this.resources.add(resource.type);  // Dodanie typu zasobu do zbioru
                    }
                }
            }
        }
    }
}
