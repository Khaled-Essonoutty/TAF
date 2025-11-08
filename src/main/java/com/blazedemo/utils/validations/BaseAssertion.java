package com.blazedemo.utils.validations;

import com.blazedemo.utils.FileUtils;
import com.blazedemo.utils.actions.ElementActions;
import com.blazedemo.utils.actions.Waits;
import com.blazedemo.utils.logs.LogsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public abstract class BaseAssertion {
    private  WebDriver driver;
    private  Waits wait;
    private  ElementActions actions;

    public BaseAssertion()
    {

    }
    protected BaseAssertion(WebDriver driver) {
        this.driver = driver;
        this.wait = new Waits(driver);
        actions = new ElementActions(driver);
    }

    protected abstract void assertEquals(String actualResult, String expectedResult, String message);
    protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);

    public BaseAssertion Equals(String actualResult, String expectedResult, String message)
    {
        assertEquals(actualResult, expectedResult, message);
        return this;
    }


    public void isElementVisible(By locator)
    {
        boolean flag = wait.fluentWait().until(d -> {
            try{
                actions.findElement(locator).isDisplayed();
                return true;
            } catch (Exception e) {
                LogsUtil.error(e.getMessage());
                return false;
            }
        });
        Assert.assertTrue(flag, "Element is not visible.");
    }

    public void assertPageUrl(String url)
    {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, url, "Unmatched URLs, Expected: " + url);
    }

    public void verifyFileDownload(String fileName, String message)
    {
        wait.fluentWait().until(
                driver1 -> FileUtils.isFileDownloaded(fileName)
        );
        assertTrue(FileUtils.isFileDownloaded(fileName), message);
    }
}
