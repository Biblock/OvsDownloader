import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("What year ? ");
        int year = scanner.nextInt();
        System.out.println("What month ? ");
        int month = scanner.nextInt();
        Month m = Month.getMonth(month, year);
        try {
            m.collectEpisodes();
            m.downloadEpisodes();
        } catch (IOException e) {
            System.out.println("Could not collect episodes");
            e.printStackTrace();
        }
    }
}
