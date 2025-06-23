package Profile;
import key.keyTest;
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
        
    }
    
    @Test
    public void getProfilewithoutUserAgent(){
        
    }
    
    @Test
    public void getProfilewithoutLocation(){
        
    }
    
    @Test
    public void getProfilewithInvalidLocation(){
        
    }
    
    @Test
    public void getProfilewithInvalidDevice(){
        
    }
    
    @Test
    public void getProfilewithInvalidUserAgent(){
        
    }
    
    @Test
    public void getProfilewithInvalidAuth(){
        
    }
    
    @Test
    public void getProfilewithValidCredentials(){
        
    }
    
}
