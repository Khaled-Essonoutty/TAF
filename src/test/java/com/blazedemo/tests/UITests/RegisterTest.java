package com.blazedemo.tests.UITests;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.SignupLoginPage;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import org.testng.annotations.*;

@UITest
public class RegisterTest extends BaseTest {

    //Variables
    String timeStamp = TimeManager.getSimpleTimestamp();

    //Test Cases
    @Test
    public void signTC() {
        new SignupLoginPage(driver)
                .navigate().signup(testData.getData("name"),
                        testData.getData("email") + timeStamp + "@gmail.com")
                .fillRegistrationForm(testData.getData("titleMale"),
                        testData.getData("password"),
                        testData.getData("day"),
                        testData.getData("month"),
                        testData.getData("year"),
                        testData.getData("firstName"),
                        testData.getData("lastName"),
                        testData.getData("companyName"),
                        testData.getData("address1"),
                        testData.getData("address2"),
                        testData.getData("country"),
                        testData.getData("state"),
                        testData.getData("city"),
                        testData.getData("zipcode"),
                        testData.getData("mobileNumber")).clickCreateAccountButton().verifySuccessfulRegistration();
    }

    @Test
    public void verifyErrorMessageAccountCreateBefore() {
        new UserManagementAPI().createRegisterUserAccount(
                        testData.getData("name"),
                        testData.getData("email") + timeStamp + "@gmail.com",
                        testData.getData("password"),
                        testData.getData("titleMale"),
                        testData.getData("day"),
                        testData.getData("month"),
                        testData.getData("year"),
                        testData.getData("firstName"),
                        testData.getData("lastName"),
                        testData.getData("companyName"),
                        testData.getData("address1"),
                        testData.getData("address2"),
                        testData.getData("country"),
                        testData.getData("state"),
                        testData.getData("city"),
                        testData.getData("zipcode"),
                        testData.getData("mobileNumber"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate().signup(testData.getData("name"), testData.getData("email") + timeStamp + "@gmail.com");
        new SignupLoginPage(driver).verifyingSignupErrorMessage("Email Address already exist!");
    }


    //Configurations
    @AfterMethod
    void afterInvocation()
    {
        new UserManagementAPI()
                .deleteUserAccount(testData.getData("email") + timeStamp + "@gmail.com",
                        testData.getData("password"))
                .verifyDeletingUserAccountSuccessfully();
    }
    @BeforeClass
    void setup() {
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        driver.browser().closeExtensionWindow();
        testData = new JsonReader("register-data");
    }

    @AfterClass
    void teardown() {
        driver.quite();
    }
}
