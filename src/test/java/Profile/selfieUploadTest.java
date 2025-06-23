package Profile;
import Authentication.RequestBodyBuilder;
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

public class selfieUploadTest extends keyTest {
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
    public void selfieUploadwithoutLocation() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location","")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithoutDevice() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location","")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithoutUserAgent() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-LocationS"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", "")
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithoutAuth() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN","")
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithInvalidDevice() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", "&*")
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithInvalidLocation() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location","&8")
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithInvalidAuth() throws Exception {
       File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN","mm")
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithOpenedMouthImage() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
	assertEquals(description,"Full face selfie not recieved. Obstructed view");    
        }
    }
    
    @Test
    public void selfieUploadwithEyesClosed() throws Exception {
       File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
	assertEquals(description,"Full face selfie not recieved. Obstructed view");    
        } 
    }
    
    @Test
    public void selfieUploadwithSpoofImage() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"VST_PROFILE_IMG_SPOOF");
	assertEquals(description,"Successfully verified Portrait/Selfie.");    
        }
    }
    
    @Test
    public void selfieUploadwithDarkImage() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
                    .then()
                    .statusCode(422)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"VST_PROFILE_IMG_DARK");
	assertEquals(description,"Unable to extract features. Selfie is not luminous.");   
        }
    }
    
    @Test
    public void selfieUploadwithFaceObstruct() throws Exception {
       File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.jpg");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.jpg",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
	assertEquals(description,"Full face selfie not recieved. Obstructed view");   
        } 
    }
    
    @Test
    public void selfieUploadwithInvalidImage() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.png");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.png",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
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
    public void selfieUploadwithValidImage() throws Exception {
        File selfie = ImageUpload.uploadImage("C:/Users/Dell/Downloads/chinese.png");
        
        try(FileInputStream fis = new FileInputStream(selfie)){
            Response response = given()
                    .header("X-GEO-Location",ConfigManager.get("Geo-Location"))
                    .header("X-Device-Id", ConfigManager.get("requestDeviceId"))
                    .header("User-Agent", ConfigManager.get("User-Agent"))
                    .header("X-AUTH-TOKEN",AuthToken)
                    .header("Accept","*/*")
                    .multiPart("selfie","chinese.png",fis,"image/jpeg")
                    .when()
                    .post("/selfie")
                    .then()
                    .statusCode(200)
                    .extract().response();
            
        String code = response.jsonPath().getString("code");
	String description = response.jsonPath().getString("description");
	assertNotNull(description, "Description is missing from the response");
	assertNotNull(code, "Code is missing");
        assertFalse(description.isEmpty(), "Description is empty");
	assertFalse(code.isEmpty(), "Code is empty");
        assertEquals(code,"GNR_OK");
	assertEquals(description,"Successfully verified Potrait/Selfie.");
           }
        }
}
