import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class NonFoodProduct extends Product{
    Double[] prices;

    public NonFoodProduct(String name, Double[] prices) {
        super(name);
        this.prices = prices;
    }

    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                    .map(value -> value.replace(",",".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                    .map(Double::valueOf) // konwertuję string na double
                    .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy

            scanner.close();

            return new NonFoodProduct(name, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static LocalDate startDate = LocalDate.of(2010, 01, 01);
    static LocalDate endDate = LocalDate.of(2022, 03, 01);

    @Override
    public double getPrice(int year, int month) {
        if(!(month >= 1 && month <= 12)){
            throw new IndexOutOfBoundsException("Zły miesiąc");
        }
        LocalDate date = LocalDate.of(year, month, 01);
        if (!(date.isAfter(startDate) && date.isBefore(endDate))){
            throw new IndexOutOfBoundsException("Zły zakres dat");
        }
        return 0;
    }
}
