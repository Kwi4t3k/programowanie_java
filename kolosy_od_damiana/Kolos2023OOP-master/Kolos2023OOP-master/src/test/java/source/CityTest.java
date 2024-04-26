package source;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    // Lista punktów definiujących ląd
    private List<Point> landPoints = new ArrayList<>();
    // Obiekt lądu
    private final Land land;

    // Konstruktor klasy CityTest
    private CityTest() {
        // Dodanie punktów do listy
        landPoints.add(new Point(0, 0));
        landPoints.add(new Point(0, 10));
        landPoints.add(new Point(10, 0));
        landPoints.add(new Point(10, 10));
        // Utworzenie obiektu lądu
        land = new Land(landPoints);
    }

    // Metoda dostarczająca strumień miast do testów
    private static final Stream<City> cityProvider(){
        Stream toReturn = Stream.of(
                new City(new Point(5, 5), "landCity", 2),
                new City(new Point(9, 9), "portCity", 5)
        );
        return toReturn;
    }

    // Metoda dostarczająca strumień zasobów do testów
    private static final Stream<Resource> resourceProvider(){
        Stream toReturn = Stream.of(
                new Resource(Resource.Type.Coal, new Point(5, 5)),
                new Resource(Resource.Type.Wood, new Point(0, 0)),
                new Resource(Resource.Type.Fish, new Point(11, 11))
        );
        return toReturn;
    }

    // Test parametryzowany sprawdzający miasta
    @ParameterizedTest
    @MethodSource({"cityProvider", "resourceProvider"})
    void testCity(City city, Resource resource, boolean bool) {
        // Tutaj powinien być kod testu
    }
}
