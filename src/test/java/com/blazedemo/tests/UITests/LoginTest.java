package com.blazedemo.tests.UITests;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.SignupLoginPage;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@UITest
public class LoginTest extends BaseTest {
    //Variables
    String timeStamp = TimeManager.getSimpleTimestamp();

    //Tests
    @Test
    public void validLoginTC() {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getData("name"),
                        testData.getData("email") + timeStamp + "@gmail.com",
                        testData.getData("password"),
                        testData.getData("firstName"),
                        testData.getData("lastName")
                )
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate().login(testData.getData("email") + timeStamp + "@gmail.com", testData.getData("password"));
        new NavigationBarComponent(driver).validateUserName(testData.getData("name"));

        new UserManagementAPI().deleteUserAccount(testData.getData("email") + timeStamp + "@gmail.com", testData.getData("password"))
                .verifyDeletingUserAccountSuccessfully();
    }

    @Test
    public void invalidLoginUsingInvalidEmailTC() {
        new UserManagementAPI().createRegisterUserAccount(
                testData.getData("name"),
                testData.getData("email") + timeStamp + "@gmail.com",
                testData.getData("password"),
                testData.getData("firstName"),
                testData.getData("lastName")
        ).verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate().login(testData.getData("email") + "@gmail.com", testData.getData("password"))
                .verifyingLoginErrorMessage(testData.getData("messages.error"));
        new UserManagementAPI().deleteUserAccount(testData.getData("email") + timeStamp + "@gmail.com", testData.getData("password"))
                .verifyDeletingUserAccountSuccessfully();
    }

    @Test
    public void invalidLoginUsingInvalidPasswordTC() {
        new UserManagementAPI().createRegisterUserAccount(
                testData.getData("name"),
                testData.getData("email") + timeStamp + "@gmail.com",
                testData.getData("password"),
                testData.getData("firstName"),
                testData.getData("lastName")
        ).verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate().login(testData.getData("email") + timeStamp + "@gmail.com", testData.getData("password") + timeStamp)
                .verifyingLoginErrorMessage(testData.getData("messages.error"));
        new UserManagementAPI().deleteUserAccount(testData.getData("email") + timeStamp + "@gmail.com", testData.getData("password"))
                .verifyDeletingUserAccountSuccessfully();
    }

    //Configurations
    @BeforeClass
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb"));
        driver.browser().closeExtensionWindow();
        testData = new JsonReader("login-data");
    }

    @AfterClass
    public void tearDown() {
        driver.quite();
    }
}
