package utils;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static final Properties props = new Properties();

	static {
	    String env = System.getProperty("env", "test"); // default: test
	    try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/" + env + ".properties")) {
	        if (input == null) {
	            throw new RuntimeException("Configuration file not found: config/" + env + ".properties");
	        }
	        props.load(input);
	        System.out.println("Loaded configuration for environment: " + env);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to load config for environment: " + env, e);
	    }
	}


    public static String get(String key) {
        return props.getProperty(key);
    }

}
