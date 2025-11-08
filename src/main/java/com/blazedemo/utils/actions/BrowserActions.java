package com.blazedemo.utils.actions;

import com.blazedemo.utils.logs.LogsUtil;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Set;

public class BrowserActions {
    private final WebDriver driver;
    private final Waits wait;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
        wait = new Waits(driver);
    }

    public String getCurrURL() {
        return driver.getCurrentUrl();
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void navigateToURL(String url) {
        LogsUtil.info("Navigating To URL: " + url);
        driver.get(url);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void closeWindow() {
        driver.close();
    }

    public void closeExtensionWindow() {
        String currentWindowHandle = driver.getWindowHandle(); //0 1
        wait.fluentWait().until(
                d ->
                {
                    return d.getWindowHandles().size() > 1; //wait until extension tab is opened
                }
        );
        for (String windowHandle : driver.getWindowHandles()) //extension tab is opened
        {
            if (!windowHandle.equals(currentWindowHandle))
                driver.switchTo().window(windowHandle).close(); //close the extension tab
        }
        driver.switchTo().window(currentWindowHandle); //switch back to the main window
        //LogsManager.info("Extension tab closed");
    }
}
