package com.softwaretestingboard.magento.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

public class WebDriverUtil {
    public static WebDriver driver;
    public static final String       currentDir            = System.getProperty("user.dir");

    public static WebDriver getWebDriver() {
        //Check if the browser is equal to chrome/firefox
        if(System.getProperty("browser").equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.get("https://magento.softwaretestingboard.com/");
        } else if (System.getProperty("browser").equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.get("https://magento.softwaretestingboard.com/");
        }
        //Mazimize current window
//        driver.manage().window().maximize();
        // Set implicit wait to 2 seconds
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        System.out.println("Browser opened successfully");
        return driver;
    }

//    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
//        TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
//        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
//        File file = new File(currentDir + "//src//test//java//reports//" + testCaseName + ".png");
//        FileUtils.copyFile(source, file);
//        return currentDir + "//src//test//java//reports//" + testCaseName + ".png";
//    }

    public static void quitWebDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}
