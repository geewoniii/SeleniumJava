package PageFactory;

import org.openqa.selenium.WebDriver;

/**
 * Factory class to manage and return instances of page objects using lazy initialization.
 * <p>
 * This class ensures that each Page Object (LoginPage, CoursePage, etc.) is instantiated only once
 * per factory lifecycle and reused afterward, promoting memory efficiency and reducing object creation overhead.
 */
public class PageObjectFactory {

    private LoginPage pageLogin;
    private CoursePage pageCourse;
    private CoursePlanPage pageCoursePlan;
    private CourseDetailPage pageCourseDetail;

    /**
     * Returns a singleton instance of the requested Page Object class.
     *
     * @param driver   The WebDriver instance used for browser interaction.
     * @param pageType The class type of the desired Page Object.
     * @param <T>      A generic type extending one of the Page Object classes.
     * @return The singleton instance of the requested Page Object.
     * @throws Exception if the requested page type is not supported or instantiation fails.
     */
    public <T> T getPage(WebDriver driver, Class<T> pageType) throws Exception {

        if (pageType.equals(LoginPage.class)) {
            if (pageLogin == null) {
                pageLogin = new LoginPage(driver);
            }
            return pageType.cast(pageLogin);
        }

        if (pageType.equals(CoursePage.class)) {
            if (pageCourse == null) {
                pageCourse = new CoursePage(driver);
            }
            return pageType.cast(pageCourse);
        }

        if (pageType.equals(CoursePlanPage.class)) {
            if (pageCoursePlan == null) {
                pageCoursePlan = new CoursePlanPage(driver);
            }
            return pageType.cast(pageCoursePlan);
        }

        if (pageType.equals(CourseDetailPage.class)) {
            if (pageCourseDetail == null) {
                pageCourseDetail = new CourseDetailPage(driver);
            }
            return pageType.cast(pageCourseDetail);
        }

        return null;
    }
}
