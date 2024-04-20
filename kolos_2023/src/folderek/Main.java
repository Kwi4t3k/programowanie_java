package folderek;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Tworzymy punkty dla lądu
        Point landPoint1 = new Point(0, 0);
        Point landPoint2 = new Point(10, 0);
        Point landPoint3 = new Point(10, 10);
        Point landPoint4 = new Point(0, 10);

        // Tworzymy ląd
        Land land = new Land(Arrays.asList(landPoint1, landPoint2, landPoint3, landPoint4));

        // Tworzymy miasto, które znajduje się na lądzie
        City city1 = new City(new Point(5, 5), "City1", 2);

        // Próbujemy dodać miasto do lądu
        try {
            land.addCity(city1);
            System.out.println("Miasto " + city1.getCityName() + " zostało dodane do lądu.");
        } catch (RuntimeException e) {
            System.out.println("Nie można dodać miasta " + e.getMessage() + ", ponieważ jego środek nie znajduje się na lądzie.");
        }

        // Tworzymy miasto, które nie znajduje się na lądzie
        City city2 = new City(new Point(15, 15), "City2", 2);

        // Próbujemy dodać miasto do lądu
        try {
            land.addCity(city2);
            System.out.println("Miasto " + city2.getCityName() + " zostało dodane do lądu.");
        } catch (RuntimeException e) {
            System.out.println("Nie można dodać miasta " + e.getMessage() + ", ponieważ jego środek nie znajduje się na lądzie.");
        }
    }
}
