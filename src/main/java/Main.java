import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Month m = Month.getMonth(10, 2012);
        try {
            m.collectEpisodes();
            m.downloadEpisodes();
        } catch (IOException e) {
            System.out.println("Could not collect episodes");
            e.printStackTrace();
        }
    }
}
