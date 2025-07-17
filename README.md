
# 📘 SeleniumJava Automation Framework

A Java-based Selenium automation framework using TestNG and Apache POI to scrape, capture, validate, and export course information from EdX. This project follows the Page Object Model (POM) design pattern and supports screenshot/video capture, Word document generation, and environment-based configuration.

---

## 📂 Project Structure

```
SeleniumJava/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── org.example/           → Entry point (Main.java)
│   │   │   ├── properties/            → PropertiesFile (read/write configs)
│   │   │   └── ui.driver/             → CaptureHelpers, ValidateUIHelpers
│   │   └── resources/                 → (reserved for config files)
│   └── test/
│       ├── java/
│       │   ├── PageFactory/           → LoginPage, CoursePage, CoursePlanPage, etc.
│       │   ├── DocGenerate/           → Apache POI Word document generation
│       │   ├── ReportTC/              → TestNG Listener, Reports
│       │   └── Tz/                    → BaseSetup, TestSuite
│       └── resources/
│           └── configs.properties     → User credentials, course URL, export paths
├── ExportData/                        → Exported screenshots & videos
├── output/                            → Exported Word documents
├── pom.xml                            → Maven dependencies
├── testng.xml                         → TestNG test suite
└── README.md                          → Project documentation
```

---

## 🔧 Configuration

### `configs.properties` (in `src/test/resources/`)
Before running tests, update this file:

```properties
email=your_email_here
password=your_password_here
courseLink=https://www.edx.org/learn/r-programming/harvard-university-data-science-r-basics
exportCapturePath=ExportData/Images
exportVideoPath=ExportData/Videos
```

> ⚠️ Do not commit credentials. Add `configs.properties` to `.gitignore`.

---

## 🚀 How to Run Tests

### 1. Clean and run all tests with Maven:
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### 2. Using IntelliJ:
- Right-click `TestSuite.java` or `testng.xml` and run directly.
    
---

## 🧪 Test Modules

| Class              | Description                            |
|--------------------|----------------------------------------|
| `LoginPage`        | Handles login functionality            |
| `CoursePage`       | Navigates course listings              |
| `CoursePlanPage`   | Selects free course plan               |
| `CourseDetailPage` | Scrapes course outline/details         |
| `DocGenerate`      | Exports course details to Word         |
| `CaptureHelpers`   | Takes screenshots or video if needed   |
| `BaseSetup`        | Initializes WebDriver                  |
| `TestSuite`        | Master suite for end-to-end flow       |

---

## 📎 Dependencies

- Java 11+
- Maven 3.x
- Selenium 4.x
- Apache POI
- TestNG
- WebDriverManager (for driver auto-config)

---
