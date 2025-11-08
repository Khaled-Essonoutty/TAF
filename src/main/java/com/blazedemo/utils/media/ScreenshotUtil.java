package com.blazedemo.utils.media;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.AllureUtil;
import com.blazedemo.utils.logs.LogsUtil;
import com.blazedemo.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.log.Log;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static final String screenshotPath = "test-outputs/screenshots/";

    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(screenshotPath + screenshotName + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getAbsolutePath());

            LogsUtil.info("Screenshot captured successfully.");
        } catch (IOException e) {
            LogsUtil.error("Failed to take screenshot. Error Message: " + e.getMessage());
        }
    }
}
