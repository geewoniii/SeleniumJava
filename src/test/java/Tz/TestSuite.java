package Tz;

import PageFactory.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import properties.PropertiesFile;
import ui.driver.ValidateUIHelpers;

import java.util.LinkedHashMap;

import static DocGenerate.DocGenerate.createDocument;

@Listeners(ReportTC.TestListener.class)
public class TestSuite {
    private  WebDriver driver;
    private  String courseName;
    private  String courseLink;
    private  String courseDescription;
    private  String courseDuration;
    private  LinkedHashMap Instructor;
    private  LinkedHashMap courseLeaner;
    private  LinkedHashMap courseOutLine;
    private ValidateUIHelpers validateUIHelpers;
    @BeforeClass
    public void setupDriver() {
        driver = new BaseSetup().setupDriver("chrome");
        validateUIHelpers = new ValidateUIHelpers(driver);
    }


    @Test
    public void TestCase1() throws Exception {
        PageObjectFactory pageFactory;

        PropertiesFile.setPropertiesFile();
        driver.get(PropertiesFile.getPropValue("courseLink"));
        pageFactory = new PageObjectFactory();
        courseLink = PropertiesFile.getPropValue("courseLink");
        courseName = pageFactory.getPage(driver, CoursePage.class).getCourseName();
        courseDescription= pageFactory.getPage(driver, CoursePage.class).getCourseDescription();
        courseDuration = pageFactory.getPage(driver, CoursePage.class).getCourseDuration();
        courseLeaner = pageFactory.getPage(driver, CoursePage.class).getCourseLearner();
        Instructor = pageFactory.getPage(driver, CoursePage.class).getCourseInstructors();
        pageFactory.getPage(driver, CoursePage.class).clickEnroll();
        pageFactory.getPage(driver, LoginPage.class).clickSignIn();
        pageFactory.getPage(driver, LoginPage.class).login(PropertiesFile.getPropValue("email"),PropertiesFile.getPropValue("password"));
        pageFactory.getPage(driver, CoursePlanPage.class).useFreePlan();
        courseOutLine = pageFactory.getPage(driver, CourseDetailPage.class).getCourseOutline();
    }

    @AfterTest
    public void createDocumentFile () {
        // Generate Template
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("Title", courseName);
        data.put("Description", courseDescription);
        data.put("Instructor", Instructor);
        data.put("What'll you learn", courseLeaner);
        data.put("CourseDetail", courseOutLine);
        data.put("CourseDuration", courseDuration);
        data.put("CourseLink", courseLink);

        // Create file
        createDocument(data,"./output/Course.docx");
    }
    @AfterTest
    public void CleanUP() throws Exception {
        try {
            System.out.println("end TCs");
        }
        catch (Exception e) {
            throw new Exception(e);
        }
        finally{
            driver.quit();

        }

    }

}
