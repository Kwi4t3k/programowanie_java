package folderek;

import java.util.ArrayList;
import java.util.List;

public class Land extends Polygon{
    public Land(List<Point> points) {
        super(points);
    }
    private List<City> cityList = new ArrayList<>();
    public void addCity(City city){
        if(this.inside(city.center)){
            cityList.add(city);
        } else {
            throw new RuntimeException(city.getCityName());
        }
    }
}
