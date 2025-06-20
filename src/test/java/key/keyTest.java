package key;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigManager;

import static io.restassured.RestAssured.given;

public class keyTest {
    protected String signOnkey;

    public keyTest() {
        this.signOnkey = fetchSignOnKey();
        if (signOnkey == null) {
            throw new IllegalStateException("signOnkey could not be fetched.");
        }
    }

    private String fetchSignOnKey() {
        RestAssured.baseURI = ConfigManager.get("baseUrl");
        Response response = given()
                .header("X-GEO-Location", ConfigManager.get("Geo-Location"))
                .header("x-device-id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .when()
                .get("/key")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.jsonPath().getString("signOnKey");
    }
}
