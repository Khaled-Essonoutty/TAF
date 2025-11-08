package com.blazedemo.utils.validations;

import com.blazedemo.utils.logs.LogsUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class Validation extends BaseAssertion{
    private static SoftAssert assertion = new SoftAssert();

    public Validation()
    {

    }
    public Validation(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertEquals(String actualResult, String expectedResult, String message) {
        assertion.assertEquals(actualResult, expectedResult, message);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        assertion.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        assertion.assertTrue(condition, message);
    }

    public static void assertAll(ITestResult testResult)
    {
        try{
            assertion.assertAll();
        } catch (AssertionError e) {
            LogsUtil.error("Failed to Assert: ", e.getMessage());
            testResult.setStatus(ITestResult.FAILURE);
            testResult.setThrowable(e);
        }finally {
            assertion = new SoftAssert();
        }
    }
}
