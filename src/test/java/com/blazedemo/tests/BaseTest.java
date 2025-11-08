package com.blazedemo.tests;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.WebDriverProvider;
import com.blazedemo.listeners.TestNGListeners;
import com.blazedemo.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;


@Listeners(TestNGListeners.class)
public class BaseTest implements WebDriverProvider {

    protected GUIDriver driver;
    protected JsonReader testData;


    @BeforeClass
    public void beforeClass()
    {
        //testData = new JsonReader("login-data");
    }

    @Override
    public WebDriver getDriver() {
        return driver.getDriver();
    }
}
