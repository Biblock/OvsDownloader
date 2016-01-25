import com.github.axet.wget.WGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Episode {
    private String urlStreaming;
    private String downloadUrl = "";
    private Month month;
    public Episode(String urlStreaming, Month month) {
        this.urlStreaming = urlStreaming; this.month = month;
    }

    public void download() throws MalformedURLException {
        URL url = new URL(downloadUrl);
        File target = new File("Episodes/" + month.toString() + "/");
        if (!target.exists()) {
            target.mkdir();
        }
        WGet wget = new WGet(url, target);
        wget.download();
    }

    public void collectDownloadLink() {
        if(downloadUrl.isEmpty()) {
            try {
                Document doc = Jsoup.connect(urlStreaming).get();
                Pattern pattern = Pattern.compile("http.*mp3");
                Matcher matcher = pattern.matcher(doc.toString());
                while (matcher.find()) {
                    downloadUrl = matcher.group(0);
                }
                if (downloadUrl.isEmpty()) {
                    System.out.println("No download link on the webpage");
                }
            } catch (IOException e) {
                System.out.println("Cannot connect link for " + e.getMessage());
            }
        }
    }
}
