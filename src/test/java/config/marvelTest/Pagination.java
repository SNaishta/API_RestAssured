package config.marvelTest;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static config.constant.Auth.*;
import static config.constant.BaseURI.MARVEL_BASE_URI;
import static config.constant.Path.GET_ALL_CHARACTERS;
import static io.restassured.RestAssured.given;

public class Pagination {

    @Test
    public void getCharactersFromAllPage() {
        int characterLimit = 100;
        int limit = 50;
        int offset = 0;

        while (offset < characterLimit) {
            System.out.println("^^^^^^^^^^^^^^^^^^^  Current Offset Value : " +  offset);

            Response response =

            given().
                    baseUri(MARVEL_BASE_URI).
                    queryParam("apikey", MARVEL_API_KEY).
                    queryParam("ts", TIMESTAMP).
                    queryParam("hash", hashcode).
                    queryParam("offset", limit).
                    queryParam("limit", limit).log().all().
                    when().
                    get(GET_ALL_CHARACTERS);

            System.out.println("^^^^^^^^^^^^^^^^^^^  Status code for this request " + response.getStatusCode() + response.statusLine());
            List<String> characterNames = response.path("data.results.findAll {it.id}.name");
            System.out.println(" Name of the characters are :  " + characterNames);
            System.out.println(" ");
            System.out.println(" ");

            offset += limit;

        }
    }
}
