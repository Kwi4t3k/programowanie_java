import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

public class FoodProduct extends Product{
    Map<String, List<Double>> pricesPerProvince = new HashMap<>();
    public FoodProduct(String name, Map<String, List<Double>> pricesPerProvince) {
        super(name);
        this.pricesPerProvince = pricesPerProvince;
    }

    @Override
    public double getPrice(int year, int month) {
        return pricesPerProvince.keySet().stream()
                .map(key -> getPrice(year, month, key)) // weźmiemy nazwy województw i wyciągniemy z nich ceny dla każdego
                .flatMapToDouble(DoubleStream::of)    // zamiana na listę doubli
                .average()     //  średnia
                .orElse(0);   // jeśli się nie uda zrobić średniej to zwraca 0
    }

    public static FoodProduct fromCsv(Path path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
            String productName = bufferedReader.readLine();
            bufferedReader.readLine(); // skip miesięcy

            String currentLine;

            Map<String, List<Double>> pricesPerProvince = new HashMap<>();

            while ((currentLine = bufferedReader.readLine()) != null){
                String[] parts = currentLine.split(";", -1);
                String province = parts[0];
                List<Double> pricePerDate = List.of(parts).subList(1, parts.length - 1).stream()
                        .map(string -> string.replaceAll(",", "."))  // zamiany "," z pliku na "."    crtl + spacja robi strzałkę
                        .map(Double::parseDouble)
                        .toList();  //zamiana listy partsów na double

                pricesPerProvince.put(province, pricePerDate); // wszystkie województwa i wszystkie ich ceny
            }

            return new FoodProduct(productName, pricesPerProvince);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(int year, int month, String province){
        var prices = pricesPerProvince.get(province);
        int index = (year - 2010) * 12 + month - 1;  //zmiana roku i miesiąca w indeks tabeli
        return prices.get(index);
    }
}
