package com.softwaretestingboard.magento.application;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.softwaretestingboard.magento.utilities.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Homepage {
    private ExtentTest test;
    public static WebDriver driver;

    @FindBy(linkText = "Create an Account")
    WebElement createAccountElem;

//    @FindBy(linkText = "Sign In ")
//    WebElement signInElem;

    @FindBy(id = "firstname")
    WebElement firstNameElem;

    @FindBy(id = "lastname")
    WebElement lastnameElem;

    @FindBy(id = "email_address")
    WebElement createAccountEmailElem;

    @FindBy(id = "password")
    WebElement createAccountPasswordElem;

    @FindBy(id = "password-confirmation")
    WebElement confirmationPasswordElem;

    @FindBy(css = ".action.submit.primary")
    WebElement createAccountBtn;

    @FindBy(xpath = "//input[@id='email']")
    WebElement emailInput;

    @FindBy(id = "pass")
    WebElement passwordInput;

    @FindBy(css = ".action.login.primary")
    WebElement loginBtn;

    public Homepage(WebDriver driver) {
        this.driver = driver;
    }

    public void Test(ExtentTest test){
        this.test = test;
    }
    public void navigateToLoginPage() {
        driver.findElement(By.xpath("//div[@class='panel header']//li[@data-label='or']")).click();
//        signInElem.click();
    }

    public boolean Login(String email, String password) {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
        test.log(Status.INFO, "Test login page");
        driver.findElement(By.cssSelector(".action.login.primary")).click();
        Helper.captureScreenshotWithFile(driver);
        return true;
    }

    public boolean CreateAccount(String email, String password, String firstName, String lastName, String confirmPassword) {
        firstNameElem.sendKeys(firstName);
        lastnameElem.sendKeys(lastName);
        createAccountEmailElem.sendKeys(email);
        createAccountPasswordElem.sendKeys(password);
        confirmationPasswordElem.sendKeys(confirmPassword);
        Helper.captureScreenshotWithFile(driver);
        createAccountBtn.click();
        return true;
    }

    public static void hoverOverProduct(WebDriver driver, WebElement productElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(productElement).click().perform();
    }

    public boolean HoverOverAProductAndAddToCompareList() {
        try{
            WebElement productElement = driver.findElement(By.cssSelector(".product-image-photo[alt$='Radiant Tee']"));
            test.log(Status.PASS, "Test Add to compare list without logging in");
            hoverOverProduct(driver, productElement);

            driver.findElement(By.xpath("//div[@class='product-addto-links']//span[contains(text(),'Add to Compare')]")).click();

            ClickOnComparisonList();
            Helper.captureScreenshotWithFile(driver);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public boolean RemoveProductFromCompareList(){
        try{
            WebElement productElement = driver.findElement(By.xpath(".product-image-photo[alt$='Argus All-Weather Tank']"));

            hoverOverProduct(driver, productElement);

            driver.findElement(By.xpath("//div[@class='product-addto-links']//span[contains(text(),'Add to Compare')]")).click();

            ClickOnComparisonList();
            driver.findElement(By.xpath("//a[@title='Remove Product']")).click();
            driver.switchTo().alert().accept();
            Helper.captureScreenshotWithFile(driver);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public boolean HoverOverAndClickAddToCompare(){
        try{
            Actions actions = new Actions(driver);
            //scroll down a page
            actions.sendKeys(Keys.DOWN).build().perform();

            WebElement productElement = driver.findElement(By.cssSelector(".product-item-link[title$='Breathe-Easy Tank']"));

            actions.moveToElement(productElement).perform();
            actions.click().perform();

            driver.findElement(By.xpath("//div[@class='product-addto-links']//span[contains(text(),'Add to Compare')]")).click();

            ClickOnComparisonList();
            Helper.captureScreenshotWithFile(driver);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public boolean AddToCompareList(){
        driver.findElement(By.xpath("//a[@class='action tocompare']")).click();
        return true;
    }


    public String ConfimationMessage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));
        String confimationMessage = driver.findElement(By.xpath("//div[@class='message-success success message']")).getText();
        System.out.println(confimationMessage);
        return confimationMessage;
    }
    public static void ClickOnComparisonList(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message-success.success.message")));

        driver.findElement(By.xpath("//a[normalize-space()='comparison list']")).click();
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-success success message']")));
//        driver.findElement(By.xpath("//a[normalize-space()='comparison list']")).click();

    }

    public String getHomepageTitle() {
        System.out.println(driver.getTitle());
        return driver.getTitle();

    }

    public void addProductToCompare() {

    }
}
