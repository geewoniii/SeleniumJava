package ui.driver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import properties.PropertiesFile;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/**
 * Utility class for capturing screenshots and screen recordings during test execution.
 */
public class CaptureHelpers extends ScreenRecorder {

    static String projectPath = System.getProperty("user.dir") + "/";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public static ScreenRecorder screenRecorder;
    public String name;

    /**
     * Captures a screenshot of the current browser window and saves it to the configured path.
     *
     * @param driver     the WebDriver instance in use.
     * @param screenName the name to be used as a prefix for the screenshot file.
     */
    public static void captureScreenshot(WebDriver driver, String screenName) {
        PropertiesFile.setPropertiesFile();
        try {
            Reporter.log("Driver for Screenshot: " + driver);
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File theDir = new File(projectPath + PropertiesFile.getPropValue("exportCapturePath"));
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            FileHandler.copy(source, new File(projectPath
                    + PropertiesFile.getPropValue("exportCapturePath") + "/"
                    + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            System.out.println("Screenshot taken: " + screenName);
            Reporter.log("Screenshot taken current URL: " + driver.getCurrentUrl(), true);
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    /**
     * Constructor for custom screen recording using Monte Media Library.
     *
     * @param cfg          the graphics configuration of the screen.
     * @param captureArea  the screen area to be recorded.
     * @param fileFormat   the format of the output video file.
     * @param screenFormat the format used for screen video encoding.
     * @param mouseFormat  the format used to encode mouse pointer (optional).
     * @param audioFormat  the format for audio (null if audio not recorded).
     * @param movieFolder  the folder where the video will be saved.
     * @param name         the name prefix for the video file.
     * @throws IOException   if there is an issue with file creation.
     * @throws AWTException  if the screen device is not accessible.
     */
    public CaptureHelpers(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                          Format mouseFormat, Format audioFormat, File movieFolder, String name)
            throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    /**
     * Creates a movie file with a timestamped name in the specified output folder.
     *
     * @param fileFormat the format of the video file.
     * @return a new File object pointing to the movie file.
     * @throws IOException if the movie folder is invalid or cannot be created.
     */
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }

        return new File(movieFolder,
                name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    /**
     * Starts screen recording for the entire display using Monte Media Library.
     *
     * @param methodName the name of the method or test case to be used as a video filename and folder.
     * @throws Exception if screen recording fails to initialize or start.
     */
    public static void startRecord(String methodName) throws Exception {
        PropertiesFile.setPropertiesFile();
        File file = new File(projectPath + PropertiesFile.getPropValue("exportVideoPath") + "/" + methodName + "/");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration();

        screenRecorder = new CaptureHelpers(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24,
                        FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                null, file, methodName);

        screenRecorder.start();
    }

    /**
     * Stops the current screen recording session.
     *
     * @throws Exception if there is an issue while stopping the recording.
     */
    public static void stopRecord() throws Exception {
        screenRecorder.stop();
    }
}
