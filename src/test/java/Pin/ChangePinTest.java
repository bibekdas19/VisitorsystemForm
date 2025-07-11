package Pin;
import Authentication.RequestBodyBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
import io.restassured.response.Response;
import java.util.Map;
import key.*;
import utils.ConfigManager;
import org.testng.annotations.*;
import utils.AuthContext;

public class ChangePinTest extends keyTest {
    String AuthToken;
    String secretkey;
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
        secretkey = signatureCreate.decryptAES(response.jsonPath().getString("sessionKey"),signOnkey);
        AuthContext.setSecretKey(secretkey);
        
    }
    
    @Test
    public void changePinwithoutDeviceId() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", "")
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
        
        
    }
    
    @Test
    public void changePinwithoutUserAgent() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", "")
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void changePinwithoutLocation() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location","")
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void changePinwithoutAuth() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN","")
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_PARAM_MISSING");
         assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void changePinwithInvalidDevice() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", "$$#")
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Invalid device Id found.");
    }
    
    @Test
    public void changePinwithInvalidLocation() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location","*77,%4")
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Invalid Geo location found.");
    }
    
    @Test
    public void changePinwithInvalidUserAgent() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", "%$%")
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Invalid user agent found.");
    }
    
    @Test
    public void changePinwithInvalidAuth() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN","mm")
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
	assertEquals(description,"Authentication failed.");
    }
    
    @Test
    public void changePinwhenNewPinEmpty() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithoutnewPin();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void changePinwhenOldPinEmpty() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithoutOldPin();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void changePinwhenNewPinPlain() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithPlainNewPin();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(500)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_ERR");
	assertEquals(description,"Decryption failed.");
    }
    
    @Test
    public void changePinwhenOldPinPlain() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithPlainOldPin();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(500)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_ERR");
	assertEquals(description,"Decryption failed.");
    }
    
    @Test
    public void changePinwhenSignatureEmpty() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithoutSignature();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(400)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Bad Request.");
    }
    
    @Test
    public void changePinwhenInvalidSignature() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithInvalidSignature();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(401)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
	assertEquals(description,"Authentication failed.");
    }
    
    @Test
    public void changePinwhenOldPinSamewithNewPin() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithsameOldandNewPin();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(409)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_CONFLICT");
	assertEquals(description,"Old Pin and new PIN cannot be the same.");
    }
    
    @Test
    public void changePinwhenNewPinCriteria() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithNewPinCriteria();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(422)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"VST_PIN_CRITERIA_FAIL");
	assertEquals(description,"Change PIN failed as PIN criteria not met.");
    }
    
    @Test
    public void changePinwhenOldPinIncorrect() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestwithIncorrectOldPin();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(500)
                .log().all()
                .extract().response();
        
        //asserting the response code 
        String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
      
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        //assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
        assertEquals(code,"GNR_ERR");
	assertEquals(description,"Error while authorizing visitor.");
    }
    
    @Test
    public void changePinwithValidCredentials() throws Exception {
        RequestBody bodybuilder = new RequestBody();
        Map<String, Object> json = bodybuilder.buildRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(json);
        
        //send request
        Response response = given()
                .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                .header("User-Agent", ConfigManager.get("User-Agent"))
                .header("X-AUTH-TOKEN",AuthToken)
                .contentType("application/json")
                .body(jsonString)
            .when()
                .put("/pin")
            .then()
                .statusCode(200)
                .log().all()
                .extract().response();
        
        //asserting the response code 
         String code = response.jsonPath().getString("code");
        String description = response.jsonPath().getString("description");
        String signature = response.jsonPath().getString("signature");

        assertNotNull(signature, "Signature is missing");
        assertNotNull(code, "code is missing from the response");
        assertNotNull(description, "description is missing from the response");

        assertFalse(signature.isEmpty(), "Signature is empty");
        assertFalse(code.isEmpty(), "code is empty");
        assertFalse(description.isEmpty(), "description is empty");
        
         assertEquals(code,"GNR_OK");
         assertEquals(description,"PIN changed successfully.");
		 
		 //verify signature
	     //matching response signature with calculated hash
//	     Map<String, Object> fields = new LinkedHashMap<>();
//	     fields.put("code", code);
//	     fields.put("description", description);
//	     
//
//	     String partialJson = objectMapper.writeValueAsString(fields);
//	     String partialSignature = signatureCreate.generateHMACSHA256(partialJson, secretKey);
//	     assertEquals(signature, partialSignature);
    }
    
    @AfterClass
    public void logOut() {
        Response response = given()
           .header("X-GEO-Location", ConfigManager.get("Geo-Location"))
           .header("X-AUTH-TOKEN",AuthToken)
           .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
           .header("User-Agent", ConfigManager.get("User-Agent"))
          .when()
          .delete("/authenticate");
       response.then().statusCode(200);
    }
}
