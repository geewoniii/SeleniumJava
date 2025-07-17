package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Page Object Model for the Course Detail Page.
 * <p>
 * This class uses Selenium WebDriver to extract the structure of a course outline
 * including section names and their corresponding lessons or links.
 */
public class CourseDetailPage {
    static WebDriver driver;

    /**
     * Constructor for the CourseDetailPage.
     *
     * @param driver the WebDriver instance to interact with the Course Detail page.
     */
    public CourseDetailPage(WebDriver driver){
        CourseDetailPage.driver = driver;
    }

    /**
     * Retrieves the course outline structure from the course detail page.
     * <p>
     * This method waits until all course sections are visible, then extracts the section
     * titles and sub-items (e.g., lesson titles and links) into a nested LinkedHashMap.
     *
     * @return a LinkedHashMap where each key is a course section title (String),
     *         and each value is a LinkedHashMap of subtopics (lesson title → URL).
     * @throws Exception if the scraping or interaction with the elements fails.
     */
    public LinkedHashMap<String, Object> getCourseOutline() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            LinkedHashMap<String, Object> courseOutLine = new LinkedHashMap<>();

            // Wait for all elements with class "collapsible-trigger"
            List<WebElement> spanElements = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.collapsible-trigger"))
            );

            for (WebElement spanElement : spanElements) {
                WebElement alignMiddleSpan = spanElement.findElement(By.className("align-middle"));
                String courseText = alignMiddleSpan.getText();

                // Expand section if there's only one
                if (spanElements.size() == 1) {
                    alignMiddleSpan.click();
                }

                // Extract all <a> links within the section
                List<WebElement> anchorElements = wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("span.align-middle> a"))
                );

                LinkedHashMap<String, String> courseSubOutline = new LinkedHashMap<>();
                for (WebElement anchorElement : anchorElements) {
                    String hrefValue = anchorElement.getAttribute("href");
                    String linkText = anchorElement.getText();
                    courseSubOutline.put(linkText, hrefValue);
                }

                courseOutLine.put(courseText, courseSubOutline);
            }

            return courseOutLine;
        } catch (Exception e) {
            throw new Exception("Get the Course Duration Failed: Exception " + e);
        }
    }
}
