package Authentication;
import key.keyTest;
import utils.ConfigManager;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.*;
import static org.testng.Assert.*;
import java.util.*;

public class authenticateTest extends keyTest {
    @Test
	public void AuthenticateWithNoLocation() throws Exception {
         RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestBody();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location","")
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
	}
        
    @Test
    public void AuthenticateWithNoDevice() throws Exception {
        RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestBody();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", "")
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    
}
    @Test
    public void AuthenticateWithNoUserAgent() throws Exception {
        RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestBody();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id",ConfigManager.get("requestDeviceId") )
                .header("User-Agent", "")
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void AuthenticateWithInvalidLocation() throws Exception {
        RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestBody();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location","@3")
                .header("X-Device-Id",ConfigManager.get("requestDeviceId") )
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid Geo location found.");
        
    }
    
    @Test
    public void AuthenticateWithInvalidDevice() throws Exception {
         RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestBody();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id","$%")
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid device Id found.");
    }
    
    @Test
    public void AuthenticatewithMissingEmail() throws Exception {
         RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestwithoutEmail();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id",ConfigManager.get("requestDeviceId") )
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Email cannot be blank..");
    }
    
    @Test
    public void AuthenticatewithInvalidEmail() throws Exception {
        RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestwithInvalidEmail();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id",ConfigManager.get("requestDeviceId") )
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid email.");
    }
    
    @Test
    public void AuthenticatewithMissingPin() throws Exception {
        RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestwithoutPin();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id",ConfigManager.get("requestDeviceId") )
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
        assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void AuthenticatewithInvalidPin() throws Exception {
         RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestwithInvalidPin();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id",ConfigManager.get("requestDeviceId") )
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
        assertEquals(description,"Authentication Failed.");
    }
    
    @Test
    public void AuthenticatewithWrongPin() throws Exception {
        RequestBodyBuilder builder = new RequestBodyBuilder();
         Map<String,Object> json = builder.buildRequestwithWrongPin();
         ObjectMapper mapper = new ObjectMapper();
         String jsonString = mapper.writeValueAsString(json);
         
         
     // Send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id",ConfigManager.get("requestDeviceId") )
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .contentType("application/json")
                .body(jsonString)
            .when()
                .post("/authenticate")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
        
     // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertTrue(code.startsWith("VST_LOGIN_REMAINING_ATTEMPT"), "Code does not start with expected prefix");
        assertEquals(description,"Authentication Failed.");
        
    }
    
}
