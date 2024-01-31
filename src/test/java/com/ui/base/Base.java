package com.ui.base;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.google.gson.JsonObject;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
    public static final Logger logger = LogManager.getLogger(Base.class);
    public WebDriver driver;
    public JsonObject jsonUserData;
    private final JSONDataConverter jsonConverter = new JSONDataConverter();
	public static ExtentTest test;
	public ExtentReports extent;
	public Properties configProperties;
	@BeforeSuite
	public void setUp() {
		WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        configProperties = new Properties();
     
        try {
        	configProperties.load(new FileReader(System.getProperty("user.dir") + "/src/test/resources/config/base.properties"));
            jsonUserData = jsonConverter.getJson(configProperties.getProperty("jsonDataPath"));
    
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}
	
	@BeforeTest
    public void startReport() {
        logger.info("Report Setup in each Test");
        ExtentSparkReporter spark = new ExtentSparkReporter("./reports/Extentreport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("MyReport");
        spark.config().setReportName("Test Report");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    private String captureScreenshot(String testName, String directoryPath) {
        String screenshotPath = null;
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedTime = currentTime.format(formatter);
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshotFile = ts.getScreenshotAs(OutputType.FILE);
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            System.out.println(testName + "    Test Name");
            screenshotPath = directoryPath + "/" + testName + formattedTime + ".png";
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(screenshotPath);
        return screenshotPath;
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        logger.info("Report Result in each Test");
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable());
            String path = captureScreenshot(result.getName(), System.getProperty("user.dir") + "/reports/Screenshots");
            test.log(Status.FAIL, "Test Case Failed");
            System.out.println(path);
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.fail("Screenshot below: " + test.addScreenCaptureFromPath(path));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed Sucessfully");
        } else {
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
    
    @AfterSuite
	public void end() throws IOException {
    	driver.quit();
		Desktop.getDesktop().browse(new File("reports/Extentreport.html").toURI());
	}

}
