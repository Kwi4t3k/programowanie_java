import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Product {
    private String name;
    public Product(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public abstract double getPrice(int year, int month);

    private static List<Product> productList = new ArrayList<>();

    public static List<Product> getProductList() {
        return productList;
    }

    public static void clearProducts(){
        productList.clear();
    }
    public static void addProducts(Function<Path, Product> fromCsv, Path pathDeer){
        try {
            Files.walk(pathDeer).forEach(path -> productList.add(fromCsv.apply(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
