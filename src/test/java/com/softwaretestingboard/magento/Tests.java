package com.softwaretestingboard.magento;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.softwaretestingboard.magento.application.Homepage;
import com.softwaretestingboard.magento.utilities.Helper;
import com.softwaretestingboard.magento.utilities.SetUp;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
public class Tests{
    public WebDriver driver;
    public Homepage homepage;
    public static final String currentDir = System.getProperty("user.dir");
    public static ExtentTest test;

    public static ExtentSparkReporter htmlReporter;
    public ExtentReports extent;

    @BeforeClass
    @Parameters({"browser", "url"})
    public void setupDriver(String browser, String url) {
        try {
            driver = SetUp.SetUpDriver(browser, url);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        homepage= new Homepage(driver);

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "test-report-" + timeStamp + ".html";
        String file = currentDir + "/src/test/java/reporting/" + reportName;
        htmlReporter = new ExtentSparkReporter(file);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Host name", "localhost");
        extent.setSystemInfo("Environment", "Automation");
        extent.setSystemInfo("user", "Sanelisiwe Madesi");

        htmlReporter.config().setDocumentTitle("UI Test Automation Project");
        htmlReporter.config().setReportName("Functional Test Automation Report");
        htmlReporter.config().setTheme(Theme.DARK);
    }

    @Test(priority = 4)
    public void validLoginTest() {
        test = extent.createTest("TC-03: valid login test", "Verify if a user can login");
        String email = "lihle@gmail.com";
        String password = "Lihle@12345";
        homepage.navigateToLoginPage();
        Assert.assertTrue(homepage.Login(email, password), "Login is unsuccessful");
    }

    @Test(priority = 5)
    public void invalidLoginTest() {
        test = extent.createTest("TC-03: Invalid login test", "Verify if a user can login");
        String email = "li@gmail.com";
        String password = "Li@12345";
        homepage.navigateToLoginPage();
        Assert.assertTrue(homepage.Login(email, password), "Login is unsuccessful");
    }

//    @Test(priority = 1)
//    public void addProductToCompareList(){
//        homepage.HoverOverAProduct();
//        Assert.assertTrue(homepage.AddToCompareList(), "Product not added to compare list");
//    }

    @Test
    public void createAccountTest(){
        String email = "sibahle@gmail.com";
        String password = "Li@12345";
        String firstName = "Sibahle";
        String lastName = "Ndaba";
        String repeatePassword = "Li@12345";

        Assert.assertTrue(homepage.CreateAccount(email, password, firstName, lastName, repeatePassword), "User not created account");
    }


    @Test(priority = 3)
    public void addProductToCompareList() {
        test = extent.createTest("TC-001: valid add to compare list test", "Verify if a not logged in user can add product to compare list");
        test.log(Status.PASS, "Add product to compare list");
        Assert.assertTrue(homepage.HoverOverAProductAndAddToCompareList(), "Product not added to compare list");
    }

    @Test(priority = 1)
    public void notLoggedInAddProductToComparelist(){
        test = extent.createTest("TC-001: valid add to compare list test", "Verify if a not logged in user can add product to compare list");
        test.log(Status.INFO, "Add product to compare list");
       Assert.assertTrue(homepage.HoverOverAndClickAddToCompare(), "User not added product to the comparison list");
    }

    @Test(priority = 2)
    public void removeProductFromCompareList(){
        test = extent.createTest("TC-001: remove product from the compare list test", "Verify if a not logged in user can remove product to compare list");
        test.log(Status.INFO, "Remove product to compare list");
        Assert.assertTrue(homepage.RemoveProductFromCompareList(), "Product removed from the comparison list");
    }

    @Test(priority = 6)
    public void getConfimationMessage(){
        String expectedText = "You added product Argus All-Weather Tank to the comparison list.";
        Assert.assertEquals(homepage.ConfimationMessage(), expectedText, "Confimation message is not displayed");
    }

    @AfterMethod
    public void testResults(ITestResult result){
        if(result.getStatus() == ITestResult.SUCCESS){
            test.log(Status.PASS, result.getTestName());
            test.addScreenCaptureFromPath(Helper.captureScreenshotWithFile(driver), "Test passed");
        }else if (result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL, result.getThrowable());
            test.addScreenCaptureFromPath(Helper.captureScreenshotWithFile(driver), "Test failed");
        }else{
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @AfterTest
    public void cleanup() {

        SetUp.tearDown(driver);
        extent.flush();
    }
}
