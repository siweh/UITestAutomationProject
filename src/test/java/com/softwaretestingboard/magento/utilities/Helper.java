package com.softwaretestingboard.magento.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static final String currentDir = System.getProperty("user.dir");
    public static String captureScreenshotWithFile(WebDriver driver){
        try {
            String fileSeparator = System.getProperty("file.separator");
            String extentReportPath = currentDir + fileSeparator+ "src" + fileSeparator+ "test" + fileSeparator + "java" + fileSeparator+ "reporting";
            String screenshotPath = extentReportPath + fileSeparator + "screenshots";

            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String screenshotName = "screenshot-" + timeStamp + ".png";
            String screenshot = screenshotPath + fileSeparator+ screenshotName;
            FileUtils.copyFile(file, new File(screenshot));
            return "." + fileSeparator + "screenshots" + fileSeparator + screenshotName;
        } catch (Exception e) {
            Assert.fail("Test failed to take a screenshot." + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
