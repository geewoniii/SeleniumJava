package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for the Login Page.
 * <p>
 * This class provides methods to interact with login components such as
 * clicking banners, switching tabs, and submitting login credentials.
 */
public class LoginPage {
    static WebDriver driver;

    // Locators for login page elements
    public static By btn_SignInBanner = By.xpath("//*[@class=\"btn btn-tertiary user-link sign-in-link\"]");
    public static By btn_SignIn = By.xpath("//*[@id=\"controlled-tab-tab-/login\"]");
    public static By txb_Username = By.xpath("//*[@id=\"emailOrUsername\"]");
    public static By txb_Password = By.xpath("//*[@id=\"password\"]");
    public static By btn_SignIn_LogIn = By.xpath("//*[@id=\"sign-in\"]");

    /**
     * Constructor for LoginPage.
     *
     * @param driver WebDriver instance used to control the browser.
     */
    public LoginPage(WebDriver driver){
        LoginPage.driver = driver;
    }

    /**
     * Clicks the "Sign In" banner on the homepage.
     *
     * @throws Exception if the banner is not found or not clickable.
     */
    public void clickSignInBanner() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(btn_SignInBanner));
            element.click();
        } catch (Exception e) {
            throw new Exception("Could not click Sign in banner. Exception: " + e);
        }
    }

    /**
     * Clicks the "Sign In" tab to switch to login form.
     *
     * @throws Exception if the tab is not found or not clickable.
     */
    public void clickSignIn() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(btn_SignIn));
            element.click();
        } catch (Exception e) {
            throw new Exception("Could not click Sign in tab. Exception: " + e);
        }
    }

    /**
     * Logs in to the application using the provided credentials.
     *
     * @param username The username or email address of the user.
     * @param password The password for the user account.
     * @throws Exception if login fails due to element issues or incorrect credentials.
     */
    public void login(String username, String password) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(btn_SignIn_LogIn));

            WebElement usernameInput = driver.findElement(txb_Username);
            WebElement passwordInput = driver.findElement(txb_Password);

            usernameInput.sendKeys(username);
            passwordInput.sendKeys(password);
            loginButton.click();
        } catch (Exception e) {
            throw new Exception("Login Error. Exception: " + e);
        }
    }
}
