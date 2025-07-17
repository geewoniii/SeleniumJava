package properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * A utility class for reading and writing key-value pairs to a properties file.
 * <p>
 * The properties file is located at: src/test/resources/configs.properties
 * <p>
 * Typical usage:
 * <pre>
 *     PropertiesFile.setPropertiesFile();
 *     String value = PropertiesFile.getPropValue("key");
 *     PropertiesFile.setPropValue("key", "value");
 * </pre>
 */
public class PropertiesFile {

    private static Properties properties;
    private static FileInputStream fileIn;
    private static FileOutputStream fileOut;

    // Get the current project path
    static String projectPath = System.getProperty("user.dir") + "/";

    // Define path of configs.properties file
    private static String propertiesFilePathRoot = "src/test/resources/configs.properties";

    /**
     * Initializes the Properties object and loads the key-value pairs from the properties file.
     * <p>
     * This method must be called before any get or set operations to ensure
     * that the properties are correctly loaded into memory from the file.
     */
    public static void setPropertiesFile() {
        properties = new Properties();
        try {
            fileIn = new FileInputStream(projectPath + propertiesFilePathRoot);
            properties.load(fileIn);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }

    /**
     * Retrieves the value associated with the specified key from the loaded properties file.
     *
     * @param KeyProp the key whose corresponding value is to be fetched from the properties file.
     * @return the value mapped to the specified key, or null if the key is not found
     *         or an exception occurs during retrieval.
     */
    public static String getPropValue(String KeyProp) {
        String value = null;
        try {
            value = properties.getProperty(KeyProp);
            System.out.println(value);
            return value;
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return value;
    }

    /**
     * Sets or updates the value for a given key in the properties file and saves the changes.
     * <p>
     * This method writes the new or updated key-value pair to the file and persists it on disk.
     *
     * @param KeyProp the key for which the value is to be set or updated.
     * @param Value   the value to associate with the given key.
     */
    public static void setPropValue(String KeyProp, String Value) {
        try {
            fileOut = new FileOutputStream(projectPath + propertiesFilePathRoot);
            properties.setProperty(KeyProp, Value);
            properties.store(fileOut, "Set new value in properties file");
            System.out.println("Set new value in file properties success.");
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }
}
