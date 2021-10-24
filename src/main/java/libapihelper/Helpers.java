package libapihelper;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

public class Helpers {

    public static void jsonXAssertEquals(String message, String actualJSON, String expectedJSON,
                                         JSONCompareMode strict) throws JSONException {
        if (!expectedJSON.equalsIgnoreCase("")) {
            JSONAssert.assertEquals(message, expectedJSON, actualJSON, strict);
        } else {
            assertEquals(actualJSON, expectedJSON, message);
        }
    }

    public static String getJsonFromResource(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\." +
                "[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        String filePath =
                System.getProperty("user.dir")
                        + File.separator + "src" + File.separator + "test" + File.separator +
                        "java" + File.separator + "resources" + File.separator + "test.properties";
        //+ File.separator + System.getProperty("env") + ".properties";

        InputStream input;
        File f = new File(filePath);
        if (f.exists() && !f.isDirectory()) {
            input = new FileInputStream(new File(filePath));
            properties.load(input);
        } else {
            System.out.println("Config File Missing :: " + filePath);
        }

        //Load base config if present
        filePath =
                System.getProperty("user.dir")
                        + File.separator + "src" + File.separator + "test" + File.separator + "resources"
                        + File.separator + "base.properties";
        f = new File(filePath);
        if (f.exists() && !f.isDirectory()) {
            input = new FileInputStream(new File(filePath));
            properties.load(input);
        }

        //Load properties to System variables
        Enumeration e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            System.setProperty(key, properties.getProperty(key));
        }
        return properties;
    }

    public static String generateRandomEmail(String domain) {
        return String.format("%s@%s", getUniqueId(), domain);
    }

    public static String getUniqueId() {
        return String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000);
    }
    public static String generateRandomWord() {
        return String.format(getUniqueId());
    }



}
