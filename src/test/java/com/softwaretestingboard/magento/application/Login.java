package com.softwaretestingboard.magento.application;

import com.aventstack.extentreports.ExtentTest;
import com.softwaretestingboard.magento.utilities.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
    public static WebDriver driver;
    private ExtentTest test;

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    public void Test(ExtentTest test){
        this.test = test;
    }

    public void navigateToLoginPage() {
        driver.findElement(By.xpath("//div[@class='panel header']//li[@data-label='or']")).click();
    }

    public boolean Login(String email, String password) {
        try {

            driver.findElement(By.id("email")).sendKeys(email);
            driver.findElement(By.id("pass")).sendKeys(password);
//            test.log(Status.INFO, "Test login page");
            Helper.captureScreenshotWithFile(driver);
            driver.findElement(By.id("send2")).click();
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
