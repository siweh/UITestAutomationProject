package com.softwaretestingboard.magento;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.softwaretestingboard.magento.application.*;
import com.softwaretestingboard.magento.utilities.ExcelReader;
import com.softwaretestingboard.magento.utilities.Helper;
import com.softwaretestingboard.magento.utilities.SetUp;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Tests{
    public WebDriver driver;
    public  CompareProducts compareProducts;
    public AddToCart addToCart;
    public Login login;
    public SearchProduct searchProduct;
    public Cart cart;
    public static final String currentDir = System.getProperty("user.dir");
    public static ExtentTest test;

    public static ExtentSparkReporter htmlReporter;
    public ExtentReports extent;
    public ExcelReader excelUtils;

    @BeforeClass
    @Parameters({"browser", "url"})
    public void setupDriver(String browser, String url) {
        try {
            driver = SetUp.SetUpDriver(browser, url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//
        compareProducts= new CompareProducts(driver);
        login = new Login(driver);
        addToCart = new AddToCart(driver);
        searchProduct = new SearchProduct(driver);
        cart = new Cart(driver);

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

    @BeforeTest
    public void setupTestData(){
        excelUtils = new ExcelReader();
        try {
            excelUtils.setExcelFileSheet("Luma test data");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//
    @Test(priority = 6)
    public void validLoginTest() {
        test = extent.createTest("TC-001: valid login test", "Verify if a user can login successfully");
        login.navigateToLoginPage();
        String email = excelUtils.getCellData(1, 1);
        String password = excelUtils.getCellData(1, 2);
        Assert.assertTrue(login.Login(email, password),"Login is unsuccessful");
    }

    @Test(priority = 5)
    public void invalidLoginTest() {
        test = extent.createTest("TC-001: Invalid login test", "Verify if a user can login with incorrect details");
        login.navigateToLoginPage();
        String email = excelUtils.getCellData(2, 1);
        String password = excelUtils.getCellData(2, 2);
        Assert.assertTrue(login.Login(email, password), "Login is successful");
    }

    @Test(priority = 4)
    public void addProductToCartNotLoggedIn(){
        test = extent.createTest("TC-004: Add product to cart", "Verify if a not logged in user can add product to cart");
        String expectedText = "You added Hero Hoodie to your shopping cart.";
        String color = excelUtils.getCellData(6, 5);
        String size = excelUtils.getCellData(6, 4);
        Assert.assertEquals(addToCart.addProductToCartWhileNotLoggedIn(color, size), expectedText, "Product not added to cart");
    }

    @Test(priority = 7)
    public void searchAProduct() throws InterruptedException {
        test = extent.createTest("TC-006: Search a product and completes order", "Verify if a logged in user can search a product and add it to cart");
        login.navigateToLoginPage();
        String email = excelUtils.getCellData(8, 1);
        String password = excelUtils.getCellData(8, 2);
        login.Login(email,password);
        Thread.sleep(2000);
        String expectedMessage = "Thank you for your order.";
        String nameOfProduct = excelUtils.getCellData(8, 3);
//        String size = excelUtils.getCellData(8,4);
//        String color = excelUtils.getCellData(8, 5);
        searchProduct.SearchAProduct(nameOfProduct);
        String firstname = excelUtils.getCellData(8,6);
        String lastname = excelUtils.getCellData(8, 7);
        String company = excelUtils.getCellData(8, 8);
        String address0 = excelUtils.getCellData(8, 9);
        String address1 = excelUtils.getCellData(8, 10);
        String address2 = excelUtils.getCellData(8, 11);
        String city = excelUtils.getCellData(8, 12);
        String province = excelUtils.getCellData(8, 13);
        String zipCode = excelUtils.getCellData(8, 14);
        String phoneNumber = excelUtils.getCellData(8, 15);
        Assert.assertEquals(cart.ProceedToCheckout(firstname, lastname, company, address0, address1, address2, city, province, zipCode, phoneNumber), expectedMessage, "Order is unsuccessful");
    }

    @Test(priority = 1)
    public void addProductToCompareList() {
        test = extent.createTest("TC-001: valid add to compare list test", "Verify if a not logged in user can add product to compare list");
        test.log(Status.INFO, "Add product to compare list");
        String expectedMessage = "You added product Radiant Tee to the comparison list.";
        Assert.assertEquals(compareProducts.HoverOverAProductAndAddToCompareList(), expectedMessage, "Product not added to compare list");
    }

    @Test(priority = 2)
    public void notLoggedInAddProductToComparelist(){
        test = extent.createTest("TC-001: valid add to compare list test", "Verify if a not logged in user can add product to compare list");
        test.log(Status.INFO, "Add product to compare list");
       Assert.assertTrue(compareProducts.HoverOverAndClickAddToCompare(), "User not added product to the comparison list");
    }

    @Test(priority = 3)
    public void removeProductFromCompareList(){
        test = extent.createTest("TC-001: remove product from the compare list test", "Verify if a not logged in user can remove product to compare list");
        test.log(Status.INFO, "Remove product to compare list");
        String errorMessage = "You removed product Argus All-Weather Tank to the conparison list.";
        Assert.assertEquals(compareProducts.RemoveProductFromCompareList(), errorMessage, "Product removed from the comparison list");
    }

//    @Test(priority = 8)
//    public void addThreeStarProductToCartWithoutOptions() throws InterruptedException {
//        test = extent.createTest("TC-005: Add product without options", "Verify if a logged in user can add a product to cart without adding product options");
//        test.log(Status.INFO, "Add product to cart without options");
//        String email = excelUtils.getCellData(7, 1);
//        String password = excelUtils.getCellData(7, 2);
//        login.Login(email, password);
//        Thread.sleep(2000);
//        String expectedText = "You need to choose options for your item.";
//        String size = excelUtils.getCellData(7,4);
//        String color = excelUtils.getCellData(7, 5);
//        Assert.assertEquals(addToCart.AddAThreeStarProduct(size,color), expectedText, "Product is not added to cart");
//    }

//    @Test(priority = 4)
//    public void getConfimationMessage(){
//        String expectedText = "You added product Argus All-Weather Tank to the comparison list.";
//        Assert.assertEquals(compareProducts.ConfimationMessage(), expectedText, "Confimation message is not displayed");
//    }

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
        if (driver == null) {
            driver.quit();
        }
        extent.flush();
    }
}
