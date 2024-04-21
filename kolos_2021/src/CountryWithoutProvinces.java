import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CountryWithoutProvinces extends Country{
    Map<LocalDate, DeathsAndCases> deathsAndCasesMap = new HashMap<>(); // Mapa która łączy datę z danymi śmierci i zakażeń
    public record DeathsAndCases(int deaths, int cases){}   // struktura do przechowywania śmierci i zakażeń
    public CountryWithoutProvinces(String countryName) {
        super(countryName);
    }

    public void addDailyStatistic(LocalDate date, int cases, int deaths){
        deathsAndCasesMap.put(date, new DeathsAndCases(deaths, cases));
    }
}
