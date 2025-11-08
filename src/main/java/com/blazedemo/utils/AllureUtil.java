package com.blazedemo.utils;

import com.blazedemo.utils.logs.LogsUtil;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.newInputStream;

public class AllureUtil {

    private static final String screenshotPath = "test-outputs/screenshots/";

    public static void deleteAllureResults() {
        File dir = new File("test-outputs/allure-results");
        System.out.println("Deleting folder: " + dir.getAbsolutePath());
        FileUtils.deleteQuietly(dir);
    }

    public static void attachScreenshot(String screenshotName) {
        try {
            Allure.addAttachment(screenshotName, newInputStream(Path.of(screenshotPath + screenshotName + ".png")));
            LogsUtil.info("Screenshot (" + screenshotName + ") attached.");
        } catch (IOException e) {
            LogsUtil.error("Failed to attach Screenshot, Error Message: " + e.getMessage());
        }
    }

    public static void addingEnvironmentInformation() {
        try {
            FileWriter environmentInfo = new FileWriter("test-outputs/allure-results/environment.properties");
            //environmentInfo.write("Windows=11\n");
            environmentInfo.write("OS_Name=" + System.getProperty("os.name") + "\n");
            environmentInfo.write("OS_Version=" + System.getProperty("os.version") + "\n");
            environmentInfo.write("OS_Arch=" + System.getProperty("os.arch") + "\n");
            environmentInfo.write("Java_Version=" + System.getProperty("java.version") + "\n");
            environmentInfo.write("Java_Vendor=" + System.getProperty("java.vendor") + "\n");

            environmentInfo.flush();  // force data to file
        } catch (IOException e) {
            LogsUtil.error(e.getMessage());
        }
    }
}
