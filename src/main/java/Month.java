import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Month {
    private static Map<String, Month> monthList = new HashMap<>();
    private List<Episode> episodeList = new ArrayList<>();
    private int month;
    private int year;

    private Month(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public void collectEpisodes() throws IOException {
        if (episodeList.isEmpty()) {
            Document doc = Jsoup.connect(generateLink()).get();
            Elements links = doc.select("a");

            for (Element link : links) {
                String url = link.attr("href");
                if (url.contains("MediaCenter")) {
                    episodeList.add(new Episode(url, this));
                }
            }

            int cursor = 1;
            int nbEp = episodeList.size();
            System.out.println(String.format("Exactly %d episode have been found", nbEp));
            for (Episode ep : episodeList) {
                System.out.println(String.format("Extracting episode [%d/%d]", cursor, nbEp));
                ep.collectDownloadLink();
                ++cursor;
            }
        }
    }

    public void downloadEpisodes() {
        int cursor = 1;
        int nbEp = episodeList.size();
        for (Episode episode : episodeList) {
            try {
                System.out.println(String.format("Downloading episode [%d/%d]", cursor, nbEp));
                episode.download();
            } catch (MalformedURLException e) {
                System.out.println("Cannot download " + e.getMessage());
            }
            ++cursor;
        }
    }

    @Override
    public String toString() {
        return  getMonth() + "-" + year;
    }

    public String getMonth() {
        return monthInLetter(String.valueOf(month));
    }

    public String generateLink() {
        return String.format("http://www.podcast-onvasgener.fr/%d-%02d-%s.html", year, month, this.getMonth());
    }

    /*** Static methods ***/
    private static String makeId(int month, int year) {
        return String.format("%d%d", month, year);
    }

    public static Month getMonth(int month, int year) {
        String id = makeId(month, year);
        Month m = monthList.containsKey(id) ? monthList.get(id) : new Month(month, year);
        monthList.put(id, m);
        return m;
    }

    public static String monthInLetter(String month) {
        String m;
        switch (Integer.valueOf(month)) {
            case 1:
                m = "janvier";
                break;
            case 2:
                m = "fevrier";
                break;
            case 3:
                m = "mars";
                break;
            case 4:
                m = "avril";
                break;
            case 5:
                m = "mai";
                break;
            case 6:
                m = "juin";
                break;
            case 7:
                m = "juillet";
                break;
            case 8:
                m = "aout";
                break;
            case 9:
                m = "septembre";
                break;
            case 10:
                m = "octobre";
                break;
            case 11:
                m = "novembre";
                break;
            case 12:
                m = "decembre";
                break;
            default:
                m = "INVALID";
        }
        return m;
    }
}
