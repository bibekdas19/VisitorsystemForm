package Profile;
import java.io.File;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class ImageUpload {
    
    public void ImageUpload(){
        
    }
    
    public static File uploadImage(String path){
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return null;
        }

        // Enable logging for request and response
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        return file;
        
    }
    
}
