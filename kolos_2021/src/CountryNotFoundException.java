public class CountryNotFoundException extends Exception{
    private final String countryName;
    public CountryNotFoundException(String countryName) {
        super(countryName);
        this.countryName = countryName;
    }

    @Override
    public String getMessage() {
        return countryName;
    }
}
