package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Page Object Model representing the Course Page.
 * <p>
 * Provides methods to extract course metadata including name, description, duration,
 * instructor details, learning outcomes, and perform interactions like enrollment.
 */
public class CoursePage {
    static WebDriver driver;

    // Locators for various elements on the course page
    public static By txt_CourseName = By.xpath("//*[@id=\"main-content\"]/div/div[1]/div/div[5]/div[1]/h1");
    public static By txt_CourseDescription = By.xpath("//*[@class=\"p\"]");
    public static By txt_CourseDuration = By.xpath("//*[@class=\"h4 mb-0\"]");
    public static By btn_CourseLearner_expand = By.xpath("//*[@id=\"outcome\"]");
    public static By btn_Enroll = By.xpath("//*[@class=\"btn btn-brand w-100\"]");

    /**
     * Constructor for CoursePage.
     *
     * @param driver WebDriver instance for interacting with the course page.
     */
    public CoursePage(WebDriver driver){
        CoursePage.driver = driver;
    }

    /**
     * Retrieves the name/title of the course.
     *
     * @return the course name as a String.
     * @throws Exception if the element cannot be found or read.
     */
    public String getCourseName() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(txt_CourseName));
            return element.getText();
        } catch (Exception e) {
            throw new Exception("Get the Course Name Failed. Exception: " + e);
        }
    }

    /**
     * Retrieves the course description.
     *
     * @return the course description as a String.
     * @throws Exception if the element cannot be found or read.
     */
    public String getCourseDescription() throws Exception{
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(txt_CourseDescription));
            return element.getText();
        } catch (Exception e) {
            throw new Exception("Get the Course Description Failed. Exception: " + e);
        }
    }

    /**
     * Retrieves the course duration.
     *
     * @return the course duration as a String (e.g., "3 weeks", "5 hours").
     * @throws Exception if the element cannot be found or read.
     */
    public String getCourseDuration() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(txt_CourseDuration));
            return element.getText();
        } catch (Exception e) {
            throw new Exception("Get the Course Duration Failed. Exception: " + e);
        }
    }

    /**
     * Retrieves a list of what the learner will learn in the course.
     * <p>
     * Clicks 'Show more' if needed to expand full list.
     *
     * @return LinkedHashMap with a single entry: key "What'll you learn", and value a list of learning items.
     * @throws Exception if the section cannot be expanded or read.
     */
    public LinkedHashMap<String, Object> getCourseLearner() throws Exception {
        try {
            LinkedHashMap<String, Object> courselearner = new LinkedHashMap<>();
            List<String> myList = new ArrayList<>();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(btn_CourseLearner_expand));
            String text = element.getText();

            if (text.contains("Show more"))
                element.click();

            // Wait for 'Show less' to confirm expansion is complete
            while (text.contains("Show less"))
                sleep(100);

            List<WebElement> liElements = driver.findElements(By.cssSelector(".mt-2.html-data ul li"));
            for (WebElement liElement : liElements) {
                myList.add(liElement.getText());
            }

            courselearner.put("What'll you learn", myList);
            return courselearner;

        } catch (Exception e) {
            throw new Exception("Get the Course Learner Section Failed. Exception: " + e);
        }
    }

    /**
     * Retrieves the course instructors' names and their titles/descriptions.
     *
     * @return LinkedHashMap where key is instructor name (h3 text) and value is subtitle/role (2nd span text).
     * @throws Exception if elements are not found or parsed correctly.
     */
    public LinkedHashMap<String, String> getCourseInstructors() throws Exception {
        try {
            List<WebElement> subClassDivElements = driver.findElements(By.cssSelector(".instructor-card"));
            LinkedHashMap<String, String> courseIntructor = new LinkedHashMap<>();

            for (WebElement subClassDivElement : subClassDivElements) {
                WebElement h3Element = subClassDivElement.findElement(By.cssSelector("h3"));
                String h3Text = h3Element.getText();

                List<WebElement> spanElements = subClassDivElement.findElements(By.cssSelector("span"));
                if (spanElements.size() > 1) {
                    String secondSpanText = spanElements.get(1).getText();
                    courseIntructor.put(h3Text, secondSpanText);
                }
            }

            return courseIntructor;
        } catch (Exception e) {
            throw new Exception("Get the Course Instructor Failed. Exception: " + e);
        }
    }

    /**
     * Clicks on the "Enroll" button to start the enrollment process.
     *
     * @throws Exception if the button cannot be clicked or located.
     */
    public void clickEnroll() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(btn_Enroll));
            element.click();
        } catch (Exception e) {
            throw new Exception("Could not click Enroll button. Exception: " + e);
        }
    }
}
