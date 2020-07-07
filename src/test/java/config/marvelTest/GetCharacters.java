package config.marvelTest;

import config.bin.Characters;
import config.common.SpecHelper;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static config.constant.Path.GET_ALL_CHARACTERS;
import static config.constant.Path.GET_UNIQUE_CHARACTER;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Every.everyItem;

public class GetCharacters extends SpecHelper {


    @Test
    public void getAllCharacters() {
        logger.info(" Test case to validate all characters have the key ID using Hamcrest Library ");

        given().
                spec(validAPI_RequestSpec).
                when().
                get(GET_ALL_CHARACTERS).
                then().log().status().
                body("data.results[0].name", equalTo("3-D Man")).
                spec(responseSpecification.response().body("data.results", everyItem(hasKey("id"))));
    }

    @Test
    public void validateResponseViaJsonSchema() {
        logger.info(" Test case to validate the response by ways of schema validation ");

        given().
                spec(validAPI_RequestSpec).
                when().
                get(GET_ALL_CHARACTERS).
                then().log().status().time(lessThan(2L), SECONDS).
        body(matchesJsonSchemaInClasspath("jsonSchema.json"));
    }

    @Test()
    public void dataCheckViaGSON() {
        logger.info(" Test case to validate the value in getCopyright filed is as expected using POJO classes");

        Response response = given().
                spec(validAPI_RequestSpec).
                pathParam("characterId", 1017100).
                when().
                get(GET_UNIQUE_CHARACTER).
                then().log().status().
                contentType(ContentType.JSON).extract().response();

            Characters characters = response.as(Characters.class, ObjectMapperType.GSON);
            Assert.assertEquals(characters.getCopyright(), "© 2020 MARVEL");

        Characters character = response.body().as(Characters.class);
        Assert.assertEquals(character.getStatus(), "Ok");
    }

    @Test
    public void InvalidEndpointAPIKey() {
        logger.info("Test case to validate unauthorised request code when the endpoint is incorrect");

        given().spec(inValidAPI_RequestSpec).
                when().get(GET_ALL_CHARACTERS).then().assertThat().statusCode(401).log().status();
    }

    @Test
    public void extractMapOfElementsWithFind() {
        logger.info("Test case to display Full map of elements for that specific is using Find{it} /Groovy GPATH ");

        Response response =  given().spec(validAPI_RequestSpec).when().get(GET_ALL_CHARACTERS);
        Map<String, ?> AllCharacters = response.path("data.results.find {it.id == 1011334}");
        System.out.println("information of characeter with id = 1011334  : " +  AllCharacters);
    }

    @Test
    public void extractNameForSpecificIDWithFind() {
        logger.info("Test case to view individual value of the map using the Map Find{it } ");

        Response response =  given().spec(validAPI_RequestSpec).when().get(GET_ALL_CHARACTERS);
        String characterName = response.path("data.results.find {it.id == 1011334}.name");
        System.out.println(" Name of the character with id = 1011334 :  " + characterName);
    }

    @Test
    public void extractListOfNameWithIDGreaterThanFindALL() {
        logger.info("Test case to view name of the character with id > 1011334 with FindAll ");

        Response response =  given().spec(validAPI_RequestSpec).when().get(GET_ALL_CHARACTERS);
        List<String> characterName = response.path("data.results.findAll {it.id >= 1011334}.name");
        System.out.println(" Name of the character with id > 1011334 :  " + characterName);
    }

    @Test
    public void extractListOfNameWithMaxId() {
        logger.info("Test case to view name of the character with highest id with max keyword ");

        Response response =  given().spec(validAPI_RequestSpec).when().get(GET_ALL_CHARACTERS);
        String characterName = response.path("data.results.max {it.id}.name");
        System.out.println(" Name of the character with id > 1011334 :  " + characterName);
    }

}






