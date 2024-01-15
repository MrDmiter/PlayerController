package api.services;

import api.config.UrlConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiService {
    protected RequestSpecification setup() {
        return given()
                .baseUri(UrlConfig.get().baseUri())
                .contentType(ContentType.JSON).log().all();
    }
}
