package base;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.*;

import utils.Constants;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;


public class BaseTest {
	public static WebDriver driver;
	public  ExtentReports extent;
	public  ExtentTest test;
	public  ExtentSparkReporter htmlReporter;
	public  ExtentTest logger;
	@BeforeTest
	public void beforeTest() {
		// Initialize the HtmlReporter
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");

		// Initialize ExtentReports and attach the HtmlReporter
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// Optional: Customize the report
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Tester Name", "Your Name");
		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("Test Report");

		// Initialize ExtentTest for logging
		test = extent.createTest("MyTest");
	}
	@BeforeMethod
	@Parameters("browser")
	public void beforeMethod(String browser, Method testMethod) {

		logger = extent.createTest(testMethod.getName());
		setupDriver(browser);
		driver.get(Constants.url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
		@AfterMethod
		public void afterMethod(ITestResult result) {
			if (result.getStatus() == ITestResult.FAILURE) {
				test.log(Status.FAIL, "Test Case Failed is " + result.getName()); // To add name in extent report
				test.log(Status.FAIL, "Test Case Failed is " + result.getThrowable()); // To add error/exception in extent report
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(Status.PASS, "Test Case Passed is " + result.getName());
			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(Status.SKIP, "Test Case Skipped is " + result.getName());
			}
			driver.quit();
		}
			@AfterTest
			public void afterTest() {
				extent.flush();
			}
			
		// Set up the WebDriver
		public void setupDriver(String browser) {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver");
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/drivers/msedgedriver");
				driver = new EdgeDriver();
			}

			driver.manage().window().maximize();
		}

	}
