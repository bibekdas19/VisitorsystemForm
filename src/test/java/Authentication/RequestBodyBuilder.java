package Authentication;

import key.keyTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import utils.ConfigManager;

public class RequestBodyBuilder extends keyTest {

    public RequestBodyBuilder() throws Exception {
        // Optional: any constructor logic here
    }

    public Map<String, Object> buildRequestBody() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", ConfigManager.get("email"));
        
        String pin = signatureCreate.encryptAES256(ConfigManager.get("pin"), signOnkey);
        credentials.put("pin", pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, signOnkey);
        jsonBody.put("signature", requestSignature);

        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithoutEmail() throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", "");
        
        String pin = signatureCreate.encryptAES256(ConfigManager.get("pin"), signOnkey);
        credentials.put("pin", pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, signOnkey);
        jsonBody.put("signature", requestSignature);

        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithoutPin() throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", ConfigManager.get("email"));
        
        //String pin = signatureCreate.encryptAES256(ConfigManager.get("pin"), signOnkey);
        credentials.put("pin", "");

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, signOnkey);
        jsonBody.put("signature", requestSignature);

        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithInvalidEmail() throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", "vivekmoco.com");
        
        String pin = signatureCreate.encryptAES256(ConfigManager.get("pin"), signOnkey);
        credentials.put("pin", pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, signOnkey);
        jsonBody.put("signature", requestSignature);

        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithInvalidPin() throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", ConfigManager.get("email"));
        
      //  String pin = signatureCreate.encryptAES256("llllwll", signOnkey);
        credentials.put("pin", "ll");

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, signOnkey);
        jsonBody.put("signature", requestSignature);

        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithWrongPin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> credentials = new LinkedHashMap<>();
        credentials.put("email", ConfigManager.get("email"));
        
        String pin = signatureCreate.encryptAES256("123456", signOnkey);
        credentials.put("pin", pin);

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("credentials", credentials);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, signOnkey);
        jsonBody.put("signature", requestSignature);

        return jsonBody;
    }
}
