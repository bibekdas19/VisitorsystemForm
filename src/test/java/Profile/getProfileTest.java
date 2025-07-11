package Profile;
import key.*;
import Authentication.RequestBodyBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import java.util.Map;
import org.testng.annotations.*;
import utils.ConfigManager;

public class getProfileTest extends keyTest {
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
    public void getProfilewithoutDevice(){
        Response response = given()
                    .header("X-GEO-Location","")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                .when()
                    .get("/profile")
                .then()
                    .statusCode(400).extract().response();
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
    public void getProfilewithoutAuth(){
        Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN","")
                .when()
                    .get("/profile")
                .then()
                    .statusCode(400).extract().response();
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
    public void getProfilewithoutUserAgent(){
        Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", "")
                    .header("X-AUTH-TOKEN",AuthToken)
                .when()
                    .get("/profile")
                .then()
                    .statusCode(400).extract().response();
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
    public void getProfilewithoutLocation(){
        Response response = given()
                    .header("X-GEO-Location","")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                .when()
                    .get("/profile")
                .then()
                    .statusCode(400).extract().response();
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
    public void getProfilewithInvalidLocation(){
         Response response = given()
                    .header("X-GEO-Location","1$#")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                .when()
                    .get("/profile")
                .then()
                    .statusCode(422).extract().response();
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
    public void getProfilewithInvalidDevice(){
         Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", "^%")
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                .when()
                    .get("/profile")
                .then()
                    .statusCode(422).extract().response();
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
    public void getProfilewithInvalidUserAgent(){
        Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", "amasasw")
                    .header("X-AUTH-TOKEN",AuthToken)
                .when()
                    .get("/profile")
                .then()
                    .statusCode(422).extract().response();
        // Extracting and asserting response values
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");

        assertNotNull(description, "Description is missing from the response");
        assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
        assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
        assertEquals(description,"Invalid user agent found.");
        
    }
    
    @Test
    public void getProfilewithInvalidAuth(){
        Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN","opas")
                .when()
                    .get("/profile")
                .then()
                    .statusCode(401).extract().response();
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
    public void getProfilewithValidCredentials(){
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id",ConfigManager.get("requestDeviceId"))
                .header("User-Agent",ConfigManager.get("User-Agent"))
                .header("X-Auth-Token",AuthToken)
            .when()
                .get("/profile")
            .then()
                .statusCode(200).extract().response();
        
        String signature = response.jsonPath().getString("signature");
        String fullName = response.jsonPath().getString("fullName");
        String country = response.jsonPath().getString("country");
        String documentNumber = response.jsonPath().getString("documentNumber");
        String dateOfBirth = response.jsonPath().getString("dateOfBirth");
        String gender = response.jsonPath().getString("gender");
        String documentType = response.jsonPath().getString("documenType");
        String status = response.jsonPath().getString("status");
        String verificationStatus = response.jsonPath().getString("verificationStatus");
        String loginAttemptCountPin = response.jsonPath().getString("loginAttemptCountPin");
        String loginAttemptCountBiometric = response.jsonPath().getString("loginAttemptCountBiometric");
        
        assertNotNull(signature, "signature is missing");
		assertNotNull(fullName, "fullname is missing");
		assertNotNull(country, "country is missing");
		assertNotNull(signature,"signature is missing");
		assertNotNull(documentNumber, "documentNumber is missing");
        assertNotNull(documentType, "documentType is missing");
        assertNotNull(dateOfBirth, "dateOfBirth is missing");
        assertNotNull(gender, "gender is missing");
        assertNotNull(status, "status is missing");
        assertNotNull(verificationStatus, "verificationStatus is missing");
        assertNotNull(loginAttemptCountPin, "loginAttemptCount is missing");
        assertNotNull(loginAttemptCountBiometric, "isBiometric is missing");

        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(fullName.isEmpty(), "fullname is missing");
        assertFalse(country.isEmpty(), "country is missing");
        assertFalse(documentNumber.isEmpty(), "documentNumber is missing");
        assertFalse(documentType.isEmpty(), "documentType is missing");
        assertFalse(dateOfBirth.isEmpty(), "dateOfBirth is missing");
        assertFalse(gender.isEmpty(), "gender is missing");
        assertFalse(status.isEmpty(), "status is missing");
        assertFalse(verificationStatus.isEmpty(), "verificationStatus is missing");
        assertFalse(loginAttemptCountPin.isEmpty(), "loginAttemptCount is missing");
        assertFalse(loginAttemptCountBiometric.isEmpty(), "isBiometric is missing");
        
      
}
}
