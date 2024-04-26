package source;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    // Lista punktów definiujących wielokąt
    private List<Point> points = new ArrayList<>();

    // Konstruktor klasy Polygon
    public Polygon(List<Point> points) {
        this.points = points; // Przypisanie wartości do zmiennej instancji
    }

    // Metoda zwracająca listę punktów wielokąta
    public List<Point> getPoints() {
        return points;
    }

    // Metoda sprawdzająca, czy dany punkt znajduje się wewnątrz wielokąta
    public boolean inside(Point point) {
        int counter = 0;
        for(int i = 0 ; i < points.size() - 1 ; i++) {
            Point pa = points.get(i);
            Point pb = points.get(i + 1);
            // Zamiana punktów, jeśli pa.y > pb.y
            if(pa.y > pb.y) {
                Point temp = pa;
                pa = pb;
                pb = temp;
            }
            // Sprawdzenie, czy punkt znajduje się pomiędzy pa.y a pb.y
            if(pa.y < point.y && point.y < pb.y) {
                double d = pb.x - pa.x;
                double x;
                // Obliczanie x na podstawie równania prostej
                if(d == 0) {
                    x = pa.x;
                } else {
                    double a = (pb.y - pa.y) / d;
                    double b = pa.y - a * pa.x;
                    x = (point.y - b) / a;
                }
                // Zwiększanie licznika, jeśli x jest mniejsze od point.x
                if(x < point.x) {
                    counter++;
                }
            }
        }
        // Zwracanie prawda/fałsz w zależności od parzystości licznika
        return counter % 2 != 0;
    }
}
