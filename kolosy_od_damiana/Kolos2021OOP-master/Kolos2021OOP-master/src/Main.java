import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    // Ścieżki do plików z danymi
    public static final String deaths = "C:\\UserFiles\\GIT\\Kolos2021OOP\\assets\\deaths.csv";
    public static final String confirmed = "C:\\UserFiles\\GIT\\Kolos2021OOP\\assets\\confirmed_cases.csv";
    public static final String output = "C:\\UserFiles\\GIT\\Kolos2021OOP\\assets\\output.txt";

    public static void main(String[] args) {
        // Tworzymy skaner do wczytywania danych z konsoli
        Scanner sc = new Scanner(System.in);
        // Wczytujemy linię tekstu
        String line = sc.nextLine();

        try {
            // Ustawiamy pliki dla klasy Country
            Country.setFiles(deaths, confirmed);
        } catch (FileNotFoundException e) {
            // Jeśli pliki nie zostaną znalezione, wyświetlamy komunikat o błędzie
            System.out.println(e.getMessage());
        }

        // Zapisujemy dane do pliku
        Country.saveToDataFile(Paths.get(output));
    }
}
