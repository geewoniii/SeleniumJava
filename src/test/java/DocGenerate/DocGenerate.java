package DocGenerate;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for generating a Microsoft Word (.docx) document that contains
 * structured course information, such as title, description, learning outcomes, instructor,
 * course outline, duration, and links.
 *
 * This class uses Apache POI library to create and style Word documents.
 */
public class DocGenerate {

    /**
     * Creates a formatted .docx document based on the given course data and saves it to the specified file path.
     *
     * @param data     A LinkedHashMap containing course content. Expected keys include:
     *                 <ul>
     *                     <li><b>Title</b> – Course title (String)</li>
     *                     <li><b>Description</b> – Course description (String)</li>
     *                     <li><b>What'll you learn</b> – LinkedHashMap&lt;String, List&lt;String&gt;&gt; of topics</li>
     *                     <li><b>Instructor</b> – LinkedHashMap&lt;String, String&gt; of instructor info</li>
     *                     <li><b>CourseDetail</b> – LinkedHashMap&lt;String, LinkedHashMap&lt;String, String&gt;&gt;</li>
     *                     <li><b>CourseDuration</b> – Duration info (String)</li>
     *                     <li><b>CourseLink</b> – URL link to the course (String)</li>
     *                 </ul>
     * @param filePath The path where the generated Word document will be saved (e.g., "./output.docx").
     */
    public static void createDocument(LinkedHashMap<String, Object> data, String filePath) {
        // Create new doc
        XWPFDocument document = new XWPFDocument();

        // Create title and format
        String title = (String) data.get("Title");
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText(title);
        titleRun.setBold(true);
        titleRun.setFontSize(18);
        titleRun.addBreak();

        // Add Description
        String description = (String) data.get("Description");
        XWPFParagraph descriptionParagraph = document.createParagraph();
        XWPFRun descriptionRun = descriptionParagraph.createRun();
        descriptionRun.setText(description);
        descriptionRun.addBreak();

        // Add "What'll you learn"
        String learnHeader = "What'll you learn";
        XWPFParagraph learnHeaderParagraph = document.createParagraph();
        XWPFRun learnHeaderRun = learnHeaderParagraph.createRun();
        learnHeaderRun.setBold(true);
        learnHeaderRun.setFontSize(16);
        learnHeaderRun.setText(learnHeader);
        learnHeaderRun.addBreak();

        LinkedHashMap<String, Object> learnList = (LinkedHashMap<String, Object>) data.get("What'll you learn");
        for (Map.Entry<String, Object> entry : learnList.entrySet()) {
            List<?> learnItem = (List<?>) entry.getValue();
            for (Object value : learnItem) {
                XWPFParagraph learnItemParagraph = document.createParagraph();
                XWPFRun learnItemRun = learnItemParagraph.createRun();
                learnItemRun.setText("- " + value);
            }
        }

        // Add "Instructor"
        LinkedHashMap<String, String> instructor = (LinkedHashMap<String, String>) data.get("Instructor");
        if (instructor != null) {
            String instructorHeader = "Instructor";
            XWPFParagraph instructorHeaderParagraph = document.createParagraph();
            XWPFRun instructorHeaderRun = instructorHeaderParagraph.createRun();
            instructorHeaderRun.addBreak();
            instructorHeaderRun.setBold(true);
            instructorHeaderRun.setFontSize(16);
            instructorHeaderRun.setText(instructorHeader);
            instructorHeaderRun.addBreak();

            for (Map.Entry<String, String> entry : instructor.entrySet()) {
                String instructorItem = entry.getKey();
                String instructorValue = entry.getValue();

                XWPFParagraph instructorParagraph = document.createParagraph();
                XWPFRun instructorRun = instructorParagraph.createRun();
                instructorRun.setBold(true);
                instructorRun.setText("- " + instructorItem + ": ");
                instructorRun.setText(instructorValue);
                instructorRun.addBreak();
            }
        }

        // Add "Course Detail"
        String courseDetailHeader = "Course Detail";
        XWPFParagraph courseDetailHeaderParagraph = document.createParagraph();
        XWPFRun courseDetailHeaderRun = courseDetailHeaderParagraph.createRun();
        courseDetailHeaderRun.setBold(true);
        courseDetailHeaderRun.setFontSize(16);
        courseDetailHeaderRun.setText(courseDetailHeader);
        courseDetailHeaderRun.addBreak();

        LinkedHashMap<String, Object> courseDetail = (LinkedHashMap<String, Object>) data.get("CourseDetail");
        for (Map.Entry<String, Object> entry : courseDetail.entrySet()) {
            String CourseOutLineTitle = entry.getKey();
            XWPFParagraph CourseOutLineTitleParagraph = document.createParagraph();
            XWPFRun CourseOutLineRun = CourseOutLineTitleParagraph.createRun();
            CourseOutLineRun.setFontSize(14);
            CourseOutLineRun.setText(CourseOutLineTitle);
            CourseOutLineRun.addBreak();

            LinkedHashMap<String, String> courseDetailOutLine = (LinkedHashMap<String, String>) entry.getValue();
            for (Map.Entry<String, String> entrydetail : courseDetailOutLine.entrySet()) {
                String CourseOutLineText = entrydetail.getKey();
                String CourseOutLineLink = entrydetail.getValue();
                XWPFParagraph CourseOutLineParagraph = document.createParagraph();
                XWPFHyperlinkRun courseOutLineLink = CourseOutLineParagraph.createHyperlinkRun(CourseOutLineLink);
                courseOutLineLink.setText("- " + CourseOutLineText);
                courseOutLineLink.addBreak();
            }
        }

        // Add "Course Duration"
        String courseDurationHeader = "Course Duration";
        XWPFParagraph courseDurationHeaderParagraph = document.createParagraph();
        XWPFRun courseDurationHeaderRun = courseDurationHeaderParagraph.createRun();
        courseDurationHeaderRun.setBold(true);
        courseDurationHeaderRun.setFontSize(16);
        courseDurationHeaderRun.setText(courseDurationHeader);
        courseDurationHeaderRun.addBreak();

        String courseDuration = (String) data.get("CourseDuration");
        XWPFParagraph courseDurationParagraph = document.createParagraph();
        XWPFRun courseDurationRun = courseDurationParagraph.createRun();
        courseDurationRun.setText("- " + courseDuration);
        courseDurationRun.addBreak();

        // Add "Course Link"
        String courseLinkHeader = "Course Link";
        XWPFParagraph courseLinkHeaderParagraph = document.createParagraph();
        XWPFRun courseLinkHeaderRun = courseLinkHeaderParagraph.createRun();
        courseLinkHeaderRun.setBold(true);
        courseLinkHeaderRun.setFontSize(16);
        courseLinkHeaderRun.setText(courseLinkHeader);
        courseLinkHeaderRun.addBreak();

        String courseLink = (String) data.get("CourseLink");
        XWPFParagraph courseLinkParagraph = document.createParagraph();
        XWPFHyperlinkRun courseLinkRun = courseLinkParagraph.createHyperlinkRun(courseLink);
        courseLinkRun.setText("- " + courseLink);
        courseLinkRun.addBreak();

        // Save to file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            document.write(outputStream);
            System.out.println("Document created successfully!");
        } catch (IOException e) {
            System.out.println("Error creating document: " + e.getMessage());
        }
    }
}
