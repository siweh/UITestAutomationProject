package com.softwaretestingboard.magento.application;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Cart {
    public static WebDriver driver;

    By billingFirstName = By.id("XXKNP6D");
    By billing_last_name = By.id("QAASXEV");
    By billing_province = By.id("KAHEHVH");
    By billing_country = By.id("U5XS9B0");
    By billingCompany = By.id("S80M0MG");
    By billingAddress0 = By.xpath("//div[@name='shippingAddress.street.0']");
    By billingAddress1 = By.xpath("//div[@name='shippingAddress.street.1']");
    By billingAddress2 = By.xpath("//div[@name='shippingAddress.street.2']");
    By billingCity = By.id("OIUEKI0");
    By postcode = By.id("GERW89Q");
    By billing_phone = By.id("WVQ8NDN");

    public Cart(WebDriver driver){
        this.driver = driver;
    }

    public void waitForElement(){
        try {
            Thread.sleep(3000); // Wait for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String ProceedToCheckout(String name, String lastName, String company, String address0, String address1, String address2, String city,String province, String zipCode, String phoneNumber){
        try{
            String message = null;
            driver.findElement(By.xpath("//a[normalize-space()='shopping cart']")).click();
            driver.findElement(By.xpath("//button[@data-role='proceed-to-checkout']")).click();
            WebElement nameInput = driver.findElement(billingFirstName);
            nameInput.sendKeys(name);
            WebElement lastNameInput = driver.findElement(billing_last_name);
            lastNameInput.sendKeys(lastName);

            WebElement companyInput = driver.findElement(billingCompany);
            companyInput.sendKeys(company);

            WebElement addressInput0 = driver.findElement(billingAddress0);
            addressInput0.sendKeys(address0);

            WebElement addressInput1 = driver.findElement(billingAddress1);
            addressInput1.sendKeys(address1);
            WebElement addressInput2 = driver.findElement(billingAddress2);
            addressInput2.sendKeys(address2);

            WebElement cityInput = driver.findElement(billingCity);
            cityInput.sendKeys(city);

            WebElement provinceInput = driver.findElement(billing_province);
            provinceInput.sendKeys(province);

            WebElement postCodeInput = driver.findElement(postcode);
            postCodeInput.sendKeys(zipCode);

            WebElement countryDropdown = driver.findElement(billing_country);
            Select dropdownCountry = new Select(countryDropdown);
            dropdownCountry.selectByVisibleText("South Africa");

            WebElement phoneNumberInput = driver.findElement(billing_phone);
            phoneNumberInput.sendKeys(phoneNumber);

            WebElement shippingMethod = driver.findElement(By.xpath("//input[@name='ko_unique_2']"));
            boolean isSelected = shippingMethod.isSelected();
            if (!isSelected) {
                shippingMethod.click();
            }

            driver.findElement(By.xpath("//button[@class='button action continue primary']")).click();

            waitForElement();
            driver.findElement(By.xpath("//button[@title='Place Order']")).click();
            waitForElement();

            message = driver.findElement(By.xpath("//div[@class='page-title-wrapper']")).getText();
            return message;
        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }
}
