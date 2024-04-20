package folderek;

import java.util.Arrays;
import java.util.List;

public class City extends Polygon{
    public final Point center;
    private String cityName;
    private double wallLength;

    public String getCityName() {
        return cityName;
    }

    public City(Point center, String cityName, double wallLength) {
        super(createSquarePoints(center, wallLength));  // zasady Javy wymagają, aby pierwszą operacją w konstruktorze klasy podrzędnej było wywołanie konstruktora klasy nadrzędnej. Dlatego super musi być użyte przed this.  (nie można używać super i this jednocześnie w konstruktorze, ponieważ oba muszą być pierwszą instrukcją w konstruktorze.)
        this.center = center;
        this.cityName = cityName;
        this.wallLength = wallLength;
    }

    private static List<Point> createSquarePoints(Point center, double wallLength){
        double halfLength = wallLength / 2;
        return Arrays.asList(
                new Point(center.x - halfLength, center.y - halfLength), // dół lewo
                new Point(center.x + halfLength, center.y - halfLength), // bół prawo
                new Point(center.x + halfLength, center.y + halfLength), // góra prawo
                new Point(center.x - halfLength, center.y - halfLength)  // góra lewo
        );
    }
}
