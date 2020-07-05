package config.marvelTest;

import config.bin.Characters;
import config.common.SpecHelper;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.constant.Path.GET_ALL_CHARACTERS;
import static config.constant.Path.GET_UNIQUE_CHARACTER;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Every.everyItem;

public class GetCharacters extends SpecHelper {


    @Test
    public void getAllCharacters() {
        logger.info(" Test case to validate all characters have the key ID using Hamcrest Library ");

        given().
                spec(requestSpecification).
                when().
                get(GET_ALL_CHARACTERS).
                then().
                body("data.results[0].name", equalTo("3-D Man")).
                spec(responseSpecification.response().body("data.results", everyItem(hasKey("id"))));
    }

    @Test
    public void validateResponseViaJsonSchema() {
        logger.info(" Test case to validate the response by ways of schema validation ");

        given().
                spec(requestSpecification).
                when().
                get(GET_ALL_CHARACTERS).
                then().
                body(matchesJsonSchemaInClasspath("jsonSchema.json"));
    }

    @Test()
    public void dataCheckViaGSON() {
        logger.info(" Test case to validate the value in getCopyright filed is as expected using POJO classes");

        Response response = given().
                spec(requestSpecification).
                when().
                get(GET_UNIQUE_CHARACTER).
                then().
                contentType(ContentType.JSON).extract().response();
        Characters characters = response.as(Characters.class, ObjectMapperType.GSON);
        Assert.assertEquals(characters.getCopyright(), "© 2020 MARVEL");
    }

    @Test
    public void InvalidEndpointAPIKey() {
        logger.info("Test case to validate unauthorised request code when the endpoint is incorrect");

        given().spec(requestSpecification).
                queryParam("apikey", "INVALID_API_KEY").
                when().get(GET_UNIQUE_CHARACTER).then().assertThat().statusCode(401);
    }
}






