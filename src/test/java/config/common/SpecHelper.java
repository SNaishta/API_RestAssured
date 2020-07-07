package config.common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import static config.constant.Auth.*;
import static config.constant.BaseURI.MARVEL_BASE_URI;

public class SpecHelper {
    public static Logger logger = LogManager.getLogger();
    public static RequestSpecification validAPI_RequestSpec;
    public static RequestSpecification inValidAPI_RequestSpec;

    @BeforeTest
    public void setUp() {
        validAPI_RequestSpec = new RequestSpecBuilder().
                setBaseUri(MARVEL_BASE_URI).
                addHeader("Content-Type", "application/json").
                addHeader("Accept", "application/json").
                addQueryParam("apikey", MARVEL_API_KEY).
                addQueryParam("ts", TIMESTAMP).
                addQueryParam("hash", getHashCode()).
                build();
        RestAssured.requestSpecification = validAPI_RequestSpec;

        inValidAPI_RequestSpec = new RequestSpecBuilder().
                setBaseUri(MARVEL_BASE_URI).
                addHeader("Content-Type", "application/json").
                addHeader("Accept", "application/json").
                addQueryParam("apikey", MARVEL_API_KEY).
                addQueryParam("ts", TIMESTAMP).
                addQueryParam("hash", "3d9e5e41512ce4f2c7990201ae8fda75").
                build();
        RestAssured.requestSpecification = inValidAPI_RequestSpec;

        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                build();
    }

}



