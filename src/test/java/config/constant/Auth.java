package config.constant;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static config.constant.BaseURI.HASHIFY_BASE_URI;
import static config.constant.Path.GET_HASH;
import static io.restassured.RestAssured.given;

public class Auth {

    public static final String MARVEL_API_KEY = System.getenv("MARVEL_PUBLIC_KEY");
    private static final String MARVEL_PRIVATE_KEY = System.getenv("MARVEL_PRIVATE_KEY");
    public static final String TIMESTAMP = "timestamp"; //add any string
    private static final String CONCATINATED_INPUT = TIMESTAMP + MARVEL_PRIVATE_KEY + MARVEL_API_KEY;

    public static String getHashCode() {
        RequestSpecification authRequestSpec = new RequestSpecBuilder().setBaseUri(HASHIFY_BASE_URI).addQueryParam("value", CONCATINATED_INPUT).build();
        RestAssured.requestSpecification = authRequestSpec;

        Response response =
                given().spec(authRequestSpec).
                        when().
                        get(GET_HASH).
                        then().
                        contentType(ContentType.JSON).extract().response();
        System.out.println("DIGEST" + response.path("Digest"));
        return response.path("Digest");
    }
}
