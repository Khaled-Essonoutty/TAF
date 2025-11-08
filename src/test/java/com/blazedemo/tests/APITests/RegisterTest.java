package com.blazedemo.tests.APITests;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {
    //Variables
    String timeStamp = TimeManager.getSimpleTimestamp();

    //Test Cases
    @Test
    public void register()
    {
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
    }

    //Configurations
    @BeforeMethod
    void setup() {
        testData = new JsonReader("register-data");
    }

}
