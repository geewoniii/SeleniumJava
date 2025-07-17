package ui.driver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

/**
 * A helper class that provides reusable methods to interact with and validate UI elements using Selenium WebDriver.
 */
public class ValidateUIHelpers {

    private WebDriver driver;
    private WebDriverWait wait;
    private int timeoutWaitForPageLoaded = 30;

    /**
     * Constructor to initialize WebDriver and WebDriverWait.
     *
     * @param _driver the WebDriver instance to be used.
     */
    public ValidateUIHelpers(WebDriver _driver){
        driver = _driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Clears the text of an input element.
     *
     * @param element the locator of the input element to clear.
     */
    public void clearText(By element){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).clear();
    }

    /**
     * Sets text value into an input field.
     *
     * @param element the locator of the input field.
     * @param value   the text to be entered.
     */
    public void setText(By element, String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).sendKeys(value);
    }

    /**
     * Clicks on the given element.
     *
     * @param element the locator of the element to click.
     */
    public void clickElement(By element){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
    }

    /**
     * Verifies if the current URL contains the expected string.
     *
     * @param url the expected URL substring.
     * @return true if current URL contains the expected string; false otherwise.
     */
    public boolean verifyUrl(String url) {
        System.out.println(driver.getCurrentUrl());
        System.out.println(url);
        return driver.getCurrentUrl().contains(url);
    }

    /**
     * Verifies if the text of a given element matches the expected text.
     *
     * @param element   the locator of the element.
     * @param textValue the expected text value.
     * @return true if the text matches; false otherwise.
     */
    public boolean verifyElementText(By element, String textValue){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        return driver.findElement(element).getText().equals(textValue);
    }

    /**
     * Checks whether an element exists in the DOM.
     *
     * @param element the locator of the element.
     * @return true if element exists; false otherwise.
     */
    public boolean verifyElementExist(By element){
        List<WebElement> listElement = driver.findElements(element);
        return listElement.size() > 0;
    }

    /**
     * Verifies if a specific text appears on the page after it has fully loaded.
     *
     * @param pageLoadedText the text to look for on the page.
     * @return true if text is found; false otherwise.
     */
    public boolean verifyPageLoaded(String pageLoadedText) {
        waitForPageLoaded();
        boolean res;
        List<WebElement> elementList = driver.findElements(By.xpath("//*[contains(text(),'" + pageLoadedText + "')]"));
        res = elementList.size() > 0;
        System.out.println("Page loaded (" + res + "): " + pageLoadedText);
        return res;
    }

    /**
     * Waits for both jQuery and JavaScript on the page to fully load.
     * <p>
     * Fails the test if the page takes longer than the timeout duration to load.
     */
    public void waitForPageLoaded(){
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver)
                            .executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true; // jQuery not present
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").toString().equals("complete");
            }
        };

        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutWaitForPageLoaded));
            wait.until(jQueryLoad);
            wait.until(jsLoad);
        } catch (Throwable error) {
            Assert.fail("Page load timeout exceeded.");
        }
    }
}
