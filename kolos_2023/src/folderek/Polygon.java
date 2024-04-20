package folderek;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points = new ArrayList<>();
    public Polygon(List<Point> points) {
        this.points = points;
    }

    public Polygon() {
    }

    public boolean inside(Point point){
        int counter = 0;
        double x, a, b;
        for (int i = 0; i < points.size(); i++) {
            Point pa = points.get(i);
            Point pb = points.get((i+1) % points.size());
            if(pa.y > pb.y){
                Point tmp = pa;
                pa = pb;
                pb = tmp;
            }
            if(pa.y < point.y && point.y < pb.y){
                double d = pb.x - pa.x;
                if(d == 0){
                    x = pa.x;
                } else {
                    a = (pb.y - pa.y) / d;
                    b = pa.y - a * pa.x;
                    x = (point.y - b) / a;
                }
                if(x < point.x){
                    counter++;
                }
            }
        }
        if(counter % 2 == 0){
            return false;
        } else {
            return true;
        }
    }
}
