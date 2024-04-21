import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public abstract class Country {
    private final String countryName;
    public String getCountryName() {
        return countryName;
    }
    public Country(String countryName) {
        this.countryName = countryName;
    }

    private static Path deathsFilePath = null;
    private static Path casesFilePath = null;

    public static void setFiles(Path deathsFilePath, Path casesFilePath) throws FileNotFoundException {
        File deathsFile = deathsFilePath.toFile();
        File casesFile = casesFilePath.toFile();

        if(deathsFile.exists() && !deathsFile.isDirectory()) {
            throw new FileNotFoundException(deathsFile.getPath());
        }
        if(casesFile.exists() && !casesFile.isDirectory()) {
            throw new FileNotFoundException(casesFile.getPath());
        }

        Country.deathsFilePath = deathsFilePath;   // zmienianie statycznych pól
        Country.casesFilePath = casesFilePath;
    }

    public static Country fromCsv(String countryName) throws CountryNotFoundException {
        try (
            BufferedReader deathsFile = new BufferedReader(new FileReader(deathsFilePath.toFile()));
            BufferedReader casesFile = new BufferedReader(new FileReader(casesFilePath.toFile()));
        ) {

            CountryColumns deathsCountryColumns = getCountryColumns(deathsFile.readLine(), countryName);
            CountryColumns casesCountryColumns = getCountryColumns(casesFile.readLine(), countryName);

            String deathLine;
            String caseLine;

            String provinceNames = deathsFile.readLine();
            casesFile.readLine();

            Country country = deathsCountryColumns.columnCount == 1
                    ? new CountryWithoutProvinces(countryName)
                    : new CountryWithProvinces(countryName, List.of());

            while((deathLine = deathsFile.readLine()) != null && (caseLine = casesFile.readLine()) != null){
                String[] deathParts = deathLine.split(";", -1);
                String[] caseParts = caseLine.split(";", -1);

                if(deathsCountryColumns.columnCount == 1){  //kraje bez prowincji (występuja raz)

                    ((CountryWithoutProvinces)country).addDailyStatistic(
                            LocalDate.parse(deathParts[0], DateTimeFormatter.ofPattern("M/d/yy")),
                            Integer.parseInt(caseParts[casesCountryColumns.firstColumnIndex]),
                            Integer.parseInt(deathParts[deathsCountryColumns.firstColumnIndex])
                    );
                }
                else {

                    List<Country> provinces = new ArrayList<>(deathsCountryColumns.columnCount);
                    var provinceNameParts = provinceNames.split(";", -1);
                    for (int i = 0; i < deathsCountryColumns.columnCount; i++) {
                        int index = deathsCountryColumns.firstColumnIndex + i;

                        var province = new CountryWithoutProvinces(provinceNameParts[index]);
                        province.addDailyStatistic(
                                LocalDate.parse(deathParts[0], DateTimeFormatter.ofPattern("M/d/yy")),
                                Integer.parseInt(caseParts[index]),
                                Integer.parseInt(deathParts[index])
                        );

                        provinces.add(province);
                    }


                }
            }
        } catch (IOException e) {}

    }

    private record CountryColumns(int firstColumnIndex, int columnCount){}   // record daje klasę domyślnie statyczną i robi to że nie trzeba konstroktorów

    private static CountryColumns getCountryColumns(String firstRow, String wantedCountry) throws CountryNotFoundException {

        List<String> parts = List.of(firstRow.split(";", -1)); // limit robi brak ograniczenia do odczytywania
        int index = parts.indexOf(wantedCountry);

        if(index == -1){
            throw new CountryNotFoundException(wantedCountry);
        }

        return new CountryColumns(
                index,      // znajduje index pierwszego wystąpienia
                Collections.frequency(parts, wantedCountry)     // zlicza wystąpienia
        );
    }
}
