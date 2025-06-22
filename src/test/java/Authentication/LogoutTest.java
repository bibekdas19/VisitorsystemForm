package Authentication;
import key.keyTest;
import utils.ConfigManager;
import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.*;
import static org.testng.Assert.*;
import java.util.*;

public class LogoutTest extends keyTest {
    String AuthToken;
    @BeforeClass
    public void LogIn() throws Exception {
         RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestBody();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        
        AuthToken = response.getHeader("X-AUTH-TOKEN");
        
    }
    
    @Test
    public void logoutwithoutDevice() {
        Response response = given()
            .header("X-GEO-Location", ConfigManager.get("Geo-Location"))
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", "")
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(400)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
        
    }
    
    @Test
    public void logoutwithoutLocation(){
        Response response = given()
            .header("X-GEO-Location","")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(400)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    
    public void logoutwithoutUserAgent(){
        Response response = given()
            .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
            .header("User-Agent", "")
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(400)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    @Test
    public void logoutwithoutAuth(){
        Response response = given()
            .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
            .header("X-AUTH-TOKEN","")
            .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(400)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
        
    }
    
    @Test
    public void logoutwithInvalidAuth(){
        Response response = given()
            .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
            .header("X-AUTH-TOKEN","xx")
            .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(401)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
        assertEquals(description,"Authentication Failed.");
    }
    
    @Test
    public void logoutwithInvalidDevice(){
         Response response = given()
            .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", "^5")
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid device Id found.");
        
    }
    
    @Test
    public void logoutwithInvalidLocation(){
         Response response = given()
            .header("X-GEO-Location","*7")
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid Geo Location found.");
    }
    
    @Test
    public void logoutwithInvalidUserAgent(){
        Response response = given()
            .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(422)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid user agent found.");
    }
    
    @Test
    public void logoutwithValidCredentials(){
        Response response = given()
            .header("X-GEO-Location", ConfigManager.get("Geo-Location"))
            .header("X-AUTH-TOKEN",AuthToken)
            .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
            .header("User-Agent", ConfigManager.get("User-Agent"))
        .when()
            .delete("/authenticate")
        .then()
            .statusCode(200)
            .extract().response();
        
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        
        assertNotNull(code, "code is missing from the response");
        assertFalse(code.isEmpty(), "code is empty in the response");
        assertNotNull(description, "description is missing from the response");
        assertFalse(description.isEmpty(), "description is empty in the response");
        //check if the code value is as per the decided
        assertEquals(code,"GNR_OK");
        assertEquals(description," Logged out successfully.");
    }
}
