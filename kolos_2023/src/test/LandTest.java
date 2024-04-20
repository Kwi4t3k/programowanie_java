package test;

import folderek.City;
import folderek.Land;
import folderek.Point;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; //assertThrows, assertTrue

public class LandTest {
    @Test // ta adnotacja musi być żeby zrobic test
    public void testAddCity(){
        // Tworzymy punkty dla lądu
        Point landPoint1 = new Point(0, 0);
        Point landPoint2 = new Point(10, 0);
        Point landPoint3 = new Point(10, 10);
        Point landPoint4 = new Point(0, 10);

        // Tworzymy ląd
        Land land = new Land(Arrays.asList(landPoint1, landPoint2, landPoint3, landPoint4));

        // Tworzymy miasto, które nie znajduje się na lądzie
        City city = new City(new Point(15, 15), "City", 2);

        // Sprawdzamy, czy rzucany jest wyjątek z odpowiednią wiadomością
        Exception exception = assertThrows(RuntimeException.class, () -> {land.addCity(city);});

        String expectedMessage = "City";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
