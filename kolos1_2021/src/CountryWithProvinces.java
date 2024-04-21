import java.util.List;

public class CountryWithProvinces extends Country{
    private List<Country> provinces;   // nazwy powinny odpowiadać nazwom prowincji
    public CountryWithProvinces(String countryName, List<Country> provinces) {
        super(countryName);
        this.provinces = provinces;
    }
}
