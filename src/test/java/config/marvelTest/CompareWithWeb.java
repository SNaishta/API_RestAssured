package config.marvelTest;

import config.bin.Characters;
import config.common.SpecHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static config.constant.Path.GET_UNIQUE_CHARACTER;
import static io.restassured.RestAssured.*;

public class CompareWithWeb extends SpecHelper {

    @Test
    public void CompareCountWithWeb() {
        logger.info(" Test case to validate the count of available comics on webpage and from Api have same count ");
        Characters characters =
                given().
                        spec(requestSpecification).
                        when().
                        get(GET_UNIQUE_CHARACTER).as(Characters.class);

        int resultCount = characters.getData().getResults().size();

        for(int i=0; i<resultCount ; i++) {
            Assert.assertEquals(getComicCountFromWeb(characters.getData().getResults().get(i).getUrls().get(i).getUrl()), (int) characters.getData().getResults().get(0).getComics().getAvailable(), "Count Comparison");
        }
    }

    private int getComicCountFromWeb(String url) {
        try {
            Document doc = Jsoup.connect(url).timeout(0).get();
            Elements temp = doc.select("span");
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(temp.get(1).text());
            while (m.find()) {
                return Integer.parseInt(m.group());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
