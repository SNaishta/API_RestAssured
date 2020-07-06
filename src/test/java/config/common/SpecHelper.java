package config.common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;

import static config.constant.Auth.*;
import static config.constant.BaseURI.MARVEL_BASE_URI;

public class SpecHelper {
     public static Logger logger = LogManager.getLogger();

    @BeforeTest
    public static void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri(MARVEL_BASE_URI).
                addHeader("Content-Type", "application/json").
                addHeader("Accept", "application/json").
                addQueryParam("apikey" , MARVEL_API_KEY).
                addQueryParam("ts", TIMESTAMP).
                addQueryParam("hash", getHashCode()).
                build();

        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                build();
    }

}



