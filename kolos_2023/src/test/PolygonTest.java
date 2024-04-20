package test; // Definiuje pakiet o nazwie "test"

// Importuje niezbędne klasy i metody do testowania
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Importuje klasy z pakietu "geometry" ten syf musi być w jakimś innym pakiecie bo jak jest bezpośrednio w src to nie działczy
import folderek.Point;
import folderek.Polygon;

import java.util.Arrays; // Importuje klasę Arrays, która zawiera metody do manipulacji tablicami
import java.util.List; // Importuje interfejs List, który reprezentuje uporządkowaną kolekcję (znane również jako sekwencję)

public class PolygonTest { // Definiuje klasę testową o nazwie "PolygonTest"
    private Polygon polygon; // Deklaruje prywatne pole "polygon" typu Polygon

    @BeforeEach // Adnotacja oznaczająca, że metoda setUp() ma być wywoływana przed każdym testem
    public void setUp() { // Metoda setUp() służy do inicjalizacji danych testowych
        List<Point> points = Arrays.asList(new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10)); // Tworzy listę punktów reprezentujących wierzchołki wielokąta
        polygon = new Polygon(points); // Tworzy nowy obiekt Polygon z listą punktów
    }

    @Test // Adnotacja oznaczająca, że metoda to test jednostkowy
    public void shouldReturnTrueWhenPointIsInsidePolygon() { // Test sprawdza, czy metoda inside() zwraca prawdę, gdy punkt jest wewnątrz wielokąta
        Point point = new Point(5, 5); // Tworzy nowy punkt
        assertTrue(polygon.inside(point)); // Sprawdza, czy punkt jest wewnątrz wielokąta
    }

    @Test // Adnotacja oznaczająca, że metoda to test jednostkowy
    public void shouldReturnFalseWhenPointIsBelowPolygon() { // Test sprawdza, czy metoda inside() zwraca fałsz, gdy punkt jest poniżej wielokąta
        Point point = new Point(5, -5); // Tworzy nowy punkt
        assertFalse(polygon.inside(point)); // Sprawdza, czy punkt jest poza wielokątem
    }

    @Test // Adnotacja oznaczająca, że metoda to test jednostkowy
    public void shouldReturnFalseWhenPointIsRightOfPolygon() { // Test sprawdza, czy metoda inside() zwraca fałsz, gdy punkt jest na prawo od wielokąta
        Point point = new Point(15, 5); // Tworzy nowy punkt
        assertFalse(polygon.inside(point)); // Sprawdza, czy punkt jest poza wielokątem
    }
}
