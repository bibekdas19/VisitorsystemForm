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
        
    }
    
    @Test
    public void AuthenticateWithInvalidDevice() throws Exception {
        
    }
    
    @Test
    public void AuthenticatewithMissingEmail() throws Exception {
        
    }
    
    @Test
    public void AuthenticatewithInvalidEmail() throws Exception {
        
    }
    
    @Test
    public void AuthenticatewithMissingPin() throws Exception {
        
    }
    
    @Test
    public void AuthenticatewithInvalidPin() throws Exception {
        
    }
    
    @Test
    public void AuthenticatewithWrongPin() throws Exception {
        
    }
    
    @Test
    public void AuthenticatewithValidDetails() throws Exception {
        
    }
}
