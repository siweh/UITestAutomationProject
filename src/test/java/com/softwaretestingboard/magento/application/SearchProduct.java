package com.softwaretestingboard.magento.application;
import com.softwaretestingboard.magento.utilities.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SearchProduct {
    public static WebDriver driver;
    public SearchProduct(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElement(){
        try {
            Thread.sleep(3000); // Wait for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean SearchAProduct(String nameOfProduct){
        Actions actions = new Actions(driver);
        String addedItemText = null;
        WebElement searchInput = driver.findElement(By.id("search"));
        searchInput.sendKeys(nameOfProduct);
        Helper.captureScreenshotWithFile(driver);
        searchInput.sendKeys(Keys.ENTER);

        waitForElement();

        WebElement searchResults = driver.findElement(By.xpath("//img[@alt='Miko Pullover Hoodie']"));
        actions.moveToElement(searchResults).perform();
        actions.click().perform();

        waitForElement();

        WebElement size = driver.findElement(By.xpath("//div[@id='option-label-size-143-item-168']"));
        WebElement color = driver.findElement(By.xpath("//div[@id='option-label-color-93-item-57']"));

//        if(size.getText().equalsIgnoreCase(productSize)){
            size.click();
//        }else if (color.getText().equalsIgnoreCase(productColor)){
            color.click();
//        }else {
//            System.out.println("Product not found.");
//        }
        waitForElement();
        driver.findElement(By.id("product-addtocart-button")).click();
        waitForElement();
        addedItemText = driver.findElement(By.xpath("//div[@data-ui-id='message-success']")).getText();
        System.out.println(addedItemText);
        driver.findElement(By.xpath("//a[normalize-space()='shopping cart']")).click();
        return true;
    }
}
