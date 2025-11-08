package com.blazedemo.utils.validations;

import com.blazedemo.utils.actions.Waits;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Verification extends BaseAssertion {

    public Verification()
    {

    }
    public Verification(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertEquals(String actualResult, String expectedResult, String message) {
        Assert.assertEquals(actualResult,expectedResult,message);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }
}
