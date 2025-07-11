package Pin;
import key.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import utils.ConfigManager;
import utils.AuthContext;


public class RequestBody extends keyTest{
    public RequestBody() throws Exception {
        
    }
    String secretKey = AuthContext.getSecretKey();
    String oldPin = signatureCreate.encryptAES256(ConfigManager.get("pin"), secretKey);
    String newPin = signatureCreate.encryptAES256(ConfigManager.get("newPin"),secretKey);
    String wOldPin = signatureCreate.encryptAES256("123654", secretKey);
    String nNewPin = signatureCreate.encryptAES256("111111", secretKey);
    public Map<String, Object> buildRequestBody() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);

        // Generate signature
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);

        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithoutOldPin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin","");
        jsonBody.put("newPin",newPin);
        
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithoutnewPin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin","");
        
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);
        
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithPlainOldPin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",ConfigManager.get("pin"));
        jsonBody.put("newPin",newPin);
        
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithPlainNewPin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",ConfigManager.get("newPin"));
        
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithIncorrectOldPin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",wOldPin);
        jsonBody.put("newPin",newPin);
        
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithoutSignature() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        String data = objectMapper.writeValueAsString(jsonBody);
       // String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature","");
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithInvalidSignature() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",newPin);
        
        String data = objectMapper.writeValueAsString(jsonBody);
       // String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", "popopopop");
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithsameOldandNewPin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",oldPin);
        
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);
        return jsonBody;
    }
    
    public Map<String, Object> buildRequestwithNewPinCriteria() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Map<String, Object> jsonBody = new LinkedHashMap<>();
        jsonBody.put("oldPin",oldPin);
        jsonBody.put("newPin",nNewPin);
        
        String data = objectMapper.writeValueAsString(jsonBody);
        String requestSignature = signatureCreate.generateHMACSHA256(data, secretKey);
        jsonBody.put("signature", requestSignature);
        return jsonBody;
    }
}
