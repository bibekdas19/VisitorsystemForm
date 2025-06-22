package Authentication;
import key.keyTest;
import utils.ConfigManager;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.*;
import static org.testng.Assert.*;
import java.util.*;

public class authenticatevalidTest extends keyTest{
    
    
    @Test
    public void authenticatewithValidCredentials() throws Exception {
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
        
        String signature = response.jsonPath().getString("signature");
        String sessionKey = response.jsonPath().getString("sessionKey");
        String deviceId = response.jsonPath().getString("deviceId");
        String fullName = response.jsonPath().getString("profile.fullName");
        String country = response.jsonPath().getString("profile.country");
        String documentNumber = response.jsonPath().getString("profile.documentNumber");
        String documentType = response.jsonPath().getString("profile.documentType");
        String dateOfBirth = response.jsonPath().getString("profile.dateOfBirth");
        String documentExpiryDate = response.jsonPath().getString("profile.documentExpiryDate");
        String responseemail = response.jsonPath().getString("profile.email");
        String gender = response.jsonPath().getString("profile.gender");
        String status = response.jsonPath().getString("profile.status");
        String verificationStatus = response.jsonPath().getString("profile.verificationStatus");
        String loginAttemptCountPin = response.jsonPath().getString("profile.loginAttemptCountPin");
        String loginAttemptCountBiometric = response.jsonPath().getString("profile.loginAttemptCountBiometric");
        String registrationDate = response.jsonPath().getString("profile.registrationDate");
        
        //check if response includes null value
        assertNotNull(signature, "signature is missing");
        assertNotNull(sessionKey, "sessionKey is missing");
        assertNotNull(deviceId, "device is missing");
        assertNotNull(fullName, "fullname is missing");
        assertNotNull(country, "country is missing");
        assertNotNull(documentNumber, "documentNumber is missing");
        assertNotNull(documentType, "documentType is missing");
        assertNotNull(dateOfBirth, "dateOfBirth is missing");
        assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
        assertNotNull(responseemail, "responseemail is missing");
        assertNotNull(gender, "gender is missing");
        assertNotNull(status, "status is missing");
        assertNotNull(verificationStatus, "verificationStatus is missing");
        assertNotNull(loginAttemptCountPin, "loginAttemptCount is missing");
        assertNotNull(loginAttemptCountBiometric, "isBiometric is missing");
        assertNotNull(registrationDate,"registration date is missing");
        
        //check is response includes empty values
        assertFalse(signature.isEmpty(), "Signature is empty in the response");
        assertFalse(sessionKey.isEmpty(), "sessionKey is missing");
        assertFalse(deviceId.isEmpty(), "device is missing");
        assertFalse(fullName.isEmpty(), "fullname is missing");
        assertFalse(country.isEmpty(), "country is missing");
        assertFalse(documentNumber.isEmpty(), "documentNumber is missing");
        assertFalse(documentType.isEmpty(), "documentType is missing");
        assertFalse(dateOfBirth.isEmpty(), "dateOfBirth is missing");
        assertFalse(documentExpiryDate.isEmpty(), "documentExpiryDate is missing");
        assertFalse(responseemail.isEmpty(), "responseemail is missing");
        assertFalse(gender.isEmpty(), "gender is missing");
        assertFalse(status.isEmpty(), "status is missing");
        assertFalse(verificationStatus.isEmpty(), "verificationStatus is missing");
        assertFalse(loginAttemptCountPin.isEmpty(), "loginAttemptCount is missing");
        assertFalse(loginAttemptCountBiometric.isEmpty(), "isBiometric is missing");
        assertFalse(registrationDate.isEmpty(),"registration date is missing");
        
        //verifying the response signature
        String responseBodyWithoutSignature = response.asString().replaceFirst("\"signature\"\\s*:\\s*\"[^\"]*\",?", ""); 
        
        String expectedSignature = signatureCreate.generateHMACSHA256(responseBodyWithoutSignature, signOnkey);
        
        // Validate
        assertEquals(signature, expectedSignature, "Response signature does not match expected value.");   
    }
}
