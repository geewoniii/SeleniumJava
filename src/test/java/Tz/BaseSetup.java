package Tz;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

/**
 * BaseSetup class is responsible for initializing WebDriver instances
 * for different browsers (Chrome, Firefox, Edge) and managing setup/teardown for test classes.
 * <p>
 * This class is meant to be extended by test classes to provide a common setup for Selenium WebDriver tests.
 */
public class BaseSetup {

    static String driverPath = "resources\\drivers\\";
    private static WebDriver driver;
    private String url = "https://google.com";

    /**
     * Get the currently initialized WebDriver instance.
     *
     * @return WebDriver object
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Get the base URL used for testing.
     *
     * @return String containing the test URL
     */
    public String getUrl(){
        return url;
    }

    /**
     * Initialize WebDriver based on the specified browser type.
     *
     * @param browserType The browser to launch (chrome, firefox, edge)
     * @return Initialized WebDriver instance
     */
    public WebDriver setupDriver(String browserType) {
        switch (browserType.trim().toLowerCase()) {
            case "chrome":
                driver = initChromeDriver();
                break;
            case "firefox":
                driver = initFirefoxDriver();
                break;
            case "edge":
                driver = initEdgeDriver();
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver();
        }
        return driver;
    }

    /**
     * Set the driver and navigate to the specified application URL.
     *
     * @param browserType The browser type to be used
     * @param appURL      The URL of the application under test
     */
    private void setDriver(String browserType, String appURL) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver();
                driver.navigate().to(appURL);
                break;
            case "firefox":
                driver = initFirefoxDriver();
                driver.navigate().to(appURL);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver();
        }
    }

    /**
     * Initialize and configure Chrome WebDriver.
     *
     * @return WebDriver instance for Chrome
     */
    private WebDriver initChromeDriver() {
        System.out.println("Launching Chrome browser...");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * Initialize and configure Edge WebDriver.
     *
     * @return WebDriver instance for Edge
     */
    private WebDriver initEdgeDriver() {
        System.out.println("Launching Edge browser...");
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * Initialize and configure Firefox WebDriver.
     *
     * @return WebDriver instance for Firefox
     */
    private WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser...");
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * Setup method run before the test class is executed.
     * Initializes WebDriver and opens the browser.
     *
     * @param browserType The browser to use for testing
     * @param webURL      The target URL to navigate to
     */
    @Parameters({"browserType", "webURL"})
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String webURL) {
        try {
            setDriver(browserType, webURL);
        } catch (Exception e) {
            System.out.println("Error..." + e.getStackTrace());
        }
    }

    /**
     * Teardown method run after the test class finishes.
     * Quits the WebDriver instance if not null.
     *
     * @throws Exception if the quit operation fails
     */
    @AfterClass
    public void tearDown() throws Exception {
        if(driver != null){
            driver.quit();
        }
    }
}
