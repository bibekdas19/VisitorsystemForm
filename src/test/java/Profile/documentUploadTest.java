package Profile;
import Authentication.RequestBodyBuilder;
import Profile.ImageUpload;
import key.keyTest;
import utils.ConfigManager;
import org.testng.annotations.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import io.restassured.response.*;
import static org.testng.Assert.*;
import java.io.FileInputStream;
import java.io.File;
import java.util.*;

public class documentUploadTest extends keyTest {
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
    public void documentUploadwithoutLocation() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location","")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(400)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Bad Request.");    
        }
    }
    
    @Test
    public void documentUploadwithoutDevice() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location","")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(400)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Bad Request.");    
        }
    }
    
    @Test
    public void documentUploadwithoutUserAgent() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-LocationS"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", "")
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(400)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Bad Request.");    
        }
    }
    
    @Test
    public void documentUploadwithoutAuth() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN","")
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(400)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Bad Request.");    
        }
    }
    
    @Test
    public void documentUploadwithInvalidDevice() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", "&*")
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Invalid device Id found.");    
        }
    }
    
    @Test
    public void documentUploadwithInvalidLocation() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location","&8")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_PARAM_MISSING");
	assertEquals(description,"Invalid Geo location found.");    
        }
    }
    
    @Test
    public void documentUploadwithInvalidAuth() throws Exception {
       File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN","mm")
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(401)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_AUTHENTICATION_FAIL");
	assertEquals(description,"Authentication Failed.");    
        } 
    }
    
    @Test
    public void documentUploadwithExpiredDocument() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Document Expired.");    
        }
    }
    
    @Test
    public void documentUploadwithUnclearDocument() throws Exception {
       File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Document Expired.");     
        } 
    }
    
    @Test
    public void documentUploadwithdifferentpersonDocument() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"VST_PROFILE_UNMATCHED");
	assertEquals(description,"Unable to match document image with selfie.Retry document upload.");    
        }
    }

    @Test
    public void documentUploadwithInvalidImage() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_INVALID_DATA");
	assertEquals(description,"Uploaded file is not a jpeg image.");    
        }
    }
    
    @Test
    public void documentUploadwithValidImage() throws Exception {
        File document = ImageUpload.uploadImage("images/openmouth.jpg");
        
        try(FileInputStream fis = new FileInputStream(document)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("document","openmouth.jpg",fis,"image/jpeg")
                    .when()
                    .post("/document")
                    .then()
                    .statusCode(200)
                    .extract().response();
            
        //asserting the details returned 
        String country = response.jsonPath().getString("country");
	String documentExpiryDate = response.jsonPath().getString("documentExpiryDate");
	String signature = response.jsonPath().getString("signature");
	String gender = response.jsonPath().getString("gender");
	String documentType = response.jsonPath().getString("documentType");
	String documentNumber = response.jsonPath().getString("documentNumber");
	String fullName = response.jsonPath().getString("fullName");
	String dateOfBirth = response.jsonPath().getString("dateOfBirth");
		
	assertNotNull(country, "country is missing from the response");
	assertNotNull(documentExpiryDate, "documentExpiryDate is missing");
	assertNotNull(signature,"signature is missing");
	assertNotNull(gender,"gender is missing");
	assertNotNull(documentType,"documentType is missing");
	assertNotNull(documentNumber);
	assertNotNull(fullName);
	assertNotNull(dateOfBirth);
        
           }
        }
    
}