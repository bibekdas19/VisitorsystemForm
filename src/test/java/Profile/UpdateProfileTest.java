package Profile;
import Authentication.RequestBodyBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import java.util.Map;
import key.*;
import org.testng.annotations.*;
import utils.ConfigManager;
public class UpdateProfileTest extends keyTest {
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
    public void updateProfilewithoutdevice() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutUserAgent() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutAuth() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutLocation() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidDevice() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidUserAgent() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidLocation() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidAuth() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutFullName() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidCountry() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutCountry() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidDocumentNumber() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutDocumentNumber() throws Exception {
        
        
    }
    
    @Test
    public void updateProfilewithInvalidDocumentType() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutDocumentType() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutDateofBirth() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidDateOfBirth() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutGender() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidGender() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidExpiryDate() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithoutSignature() throws Exception {
        
    }
    
    @Test
    public void updateProfilewithInvalidSignature() throws Exception {
        
        
    }
    
    @Test
    public void updateProfilewithValidParameters() throws Exception {
        
    }
    
    @AfterClass
    public void logOut() {
    }
}
