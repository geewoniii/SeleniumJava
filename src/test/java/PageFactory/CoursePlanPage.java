package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model representing the Course Plan Page.
 * <p>
 * Allows user to interact with the available course plans, such as selecting the free audit option.
 */
public class CoursePlanPage {
    static WebDriver driver;

    // Locator for the "Use Free Plan" button (Audit track)
    public static By btn_useFreePlan = By.xpath("//*[@id=\"track_selection_audit\"]/span");

    /**
     * Constructor for CoursePlanPage.
     *
     * @param driver WebDriver instance used to interact with the Course Plan page.
     */
    public CoursePlanPage(WebDriver driver){
        CoursePlanPage.driver = driver;
    }

    /**
     * Clicks the "Use Free Plan" (Audit track) button.
     *
     * @throws Exception if the button is not found or cannot be clicked.
     */
    public void useFreePlan() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(btn_useFreePlan));
            element.click();
        } catch (Exception e) {
            throw new Exception("Could not click 'Use Free Plan' button. Exception: " + e);
        }
    }
}
