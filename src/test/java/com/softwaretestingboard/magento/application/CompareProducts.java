package com.softwaretestingboard.magento.application;

import com.aventstack.extentreports.ExtentTest;
import com.softwaretestingboard.magento.utilities.Helper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class CompareProducts {
    private ExtentTest test;
    public static WebDriver driver;
    public CompareProducts(WebDriver driver) {
        this.driver = driver;
    }

    public void Test(ExtentTest test){
        this.test = test;
    }

    public void waitForElement(){
        try {
            Thread.sleep(3000); // Wait for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void hoverOverProduct(WebDriver driver, WebElement productElement) {
        Actions actions = new Actions(driver);
        //scroll down a page
        actions.sendKeys(Keys.DOWN).build().perform();
        actions.moveToElement(productElement).click().perform();
    }

    public String HoverOverAProductAndAddToCompareList() {
        try{
            String message = null;
            WebElement productElement = driver.findElement(By.xpath("//img[@alt='Radiant Tee']"));
//            test.log(Status.PASS, "Test Add to compare list without logging in");
            hoverOverProduct(driver, productElement);
            waitForElement();
            driver.findElement(By.xpath("//a[@data-role='add-to-links']")).click();
            Helper.captureScreenshotWithFile(driver);
            message = driver.findElement(By.xpath("//div[@data-ui-id='message-success']")).getText();
            ClickOnComparisonList();

            return message;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String RemoveProductFromCompareList(){
        try{
            String removeMessage = null;
            WebElement productElement = driver.findElement(By.xpath("(//img[@alt='Argus All-Weather Tank'])[1]"));
            hoverOverProduct(driver, productElement);
            waitForElement();
            driver.findElement(By.xpath("//a[@data-role='add-to-links']")).click();
            waitForElement();
            ClickOnComparisonList();
            driver.findElement(By.xpath("//a[@title='Remove Product']")).click();
            Helper.captureScreenshotWithFile(driver);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            removeMessage = driver.findElement(By.xpath("//div[@data-ui-id='message-success']")).getText();
            return removeMessage;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public boolean HoverOverAndClickAddToCompare(){
        try{
            Actions actions = new Actions(driver);
            //scroll down a page
            actions.sendKeys(Keys.DOWN).build().perform();

            WebElement productElement = driver.findElement(By.xpath("//img[@alt='Breathe-Easy Tank']"));

            actions.moveToElement(productElement).perform();
            actions.click().perform();

            waitForElement();
            driver.findElement(By.xpath("//a[@data-role='add-to-links']")).click();
            Helper.captureScreenshotWithFile(driver);
            waitForElement();
            ClickOnComparisonList();
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public boolean AddToCompareList(){
        driver.findElement(By.xpath("//a[@class='action tocompare']")).click();
        return true;
    }


    public static String ConfimationMessage(){
        String confimationMessage = driver.findElement(By.xpath("//div[@data-ui-id='message-success']")).getText();
        System.out.println(confimationMessage);
        return confimationMessage;
    }

    public static void ClickOnComparisonList(){
        driver.findElement(By.xpath("//a[normalize-space()='comparison list']")).click();

    }

    public String getHomepageTitle() {
        System.out.println(driver.getTitle());
        return driver.getTitle();

    }
}
