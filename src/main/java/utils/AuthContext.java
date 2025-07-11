package utils;

public class AuthContext {
    private static String secretKey;

    public static String getSecretKey() {
        return secretKey;
    }

    public static void setSecretKey(String key) {
        secretKey = key;
    }
}
