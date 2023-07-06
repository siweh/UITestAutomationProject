package com.softwaretestingboard.magento.application;

import com.softwaretestingboard.magento.utilities.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AddToCart {
    public WebDriver driver;
    By productsBySize = By.cssSelector(".swatch-option.text");
    By productsByColor = By.cssSelector("div[class='swatch-attribute color'] div[role='listbox']");

    public AddToCart(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElement(){
        try {
            Thread.sleep(3000); // Wait for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String addProductToCartWhileNotLoggedIn(String size, String color){
        try{
            String addedItemText = null;
            Actions actions = new Actions(driver);
            //scroll down a page
            actions.sendKeys(Keys.DOWN).build().perform();

            WebElement productElement = driver.findElement(By.xpath("(//img[@alt='Hero Hoodie'])[1]"));

            actions.moveToElement(productElement).perform();
            actions.click().perform();

            waitForElement();

            WebElement productSize = driver.findElement(By.xpath("//div[@id='option-label-size-143-item-168']"));
            WebElement productColor = driver.findElement(By.xpath("//div[@id='option-label-color-93-item-53']"));

            productColor.getText();

            if(productColor.getText().equalsIgnoreCase(color)){
                productColor.click();
            }else if (productSize.getText().equalsIgnoreCase(size)){
                productSize.click();
            }else {
                System.out.println("Product not found.");
            }

            Helper.captureScreenshotWithFile(driver);
            driver.findElement(By.xpath("//button[@title='Add to Cart']")).click();
            waitForElement();
            addedItemText = driver.findElement(By.xpath("//div[@data-ui-id='message-success']")).getText();
            System.out.println(addedItemText);
            return addedItemText;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String AddAThreeStarProduct(String size, String color){
        try {
            String errorMessage = null;
            driver.findElement(By.xpath("//a[@id='ui-id-5']")).click();
            driver.findElement(By.linkText("Tops")).click();
            driver.findElement(By.xpath("//a[contains(text(),'Tees')]")).click();

            Actions actions = new Actions(driver);

            WebElement productElement = driver.findElement(By.xpath("//img[@alt='Strike Endurance Tee']"));
            actions.moveToElement(productElement).perform();
            actions.click().perform();

            waitForElement();
//            WebElement productSize = driver.findElement(By.xpath("//div[@id='option-label-size-143-item-168']"));
//            WebElement productColor = driver.findElement(By.xpath("//div[@id='option-label-color-93-item-50']"));

            driver.findElement(By.id("product-addtocart-button")).click();
            waitForElement();
            errorMessage = driver.findElement(By.xpath("//div[@data-ui-id='message-success']")).getText();
            System.out.println(errorMessage);
            return errorMessage;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
