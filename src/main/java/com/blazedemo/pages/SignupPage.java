package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.logs.LogsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    //Variables
    private GUIDriver driver;

    //Locators
    private final By genderMr = By.id("#id_gender1");
    private final By genderMs = By.id("#id_gender2");
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By password = By.id("password");
    private final By selectDays = By.id("days");
    private final By selectMonths = By.id("months");
    private final By selectYears = By.id("years");
    private final By newsLetterCheckBox = By.id("newsletter");
    private final By optinCheckBox = By.id("optin");

    /* Address Information */
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By firstAddress = By.id("address1");
    private final By secondAddress = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipCode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By submitButton = By.cssSelector("[data-qa='create-account']");

    private final By accountCreatedMessage = By.cssSelector("[data-qa = 'account-created'] b");
    private final By continueButton = By.cssSelector("[data-qa = 'continue-button'] ");


    //Constructor
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Actions
    private SignupPage chooseGender(String gender) {
        driver.element().clickButton(By.cssSelector("[value='" + gender + "']"));
        return this;
    }

    @Step("Filling Registration Form")
    public SignupPage fillRegistrationForm(String title,
                                             String passwordText,
                                             String selectDaysText,
                                             String selectMonthsText,
                                             String selectYearsText,
                                             String firstNameText,
                                             String lastNameText,
                                             String companyText,
                                             String firstAddressText,
                                             String secondAddressText,
                                             String countryText,
                                             String stateText,
                                             String cityText,
                                             String zipCodeText,
                                             String mobileNumberText)
    {
        chooseGender(title);
        driver.element().sendData(passwordText, password);
        driver.element().selectFromDropDown(selectDaysText, selectDays);
        driver.element().selectFromDropDown(selectMonthsText, selectMonths);
        driver.element().selectFromDropDown(selectYearsText, selectYears);
        driver.element().clickButton(newsLetterCheckBox);
        driver.element().clickButton(optinCheckBox);
        driver.element().sendData(firstNameText, firstName);
        driver.element().sendData(lastNameText, lastName);
        driver.element().sendData(companyText, company);
        driver.element().sendData(firstAddressText, firstAddress);
        driver.element().sendData(secondAddressText, secondAddress);
        driver.element().selectFromDropDown(countryText, country);
        driver.element().sendData(stateText, state);
        driver.element().sendData(cityText, city);
        driver.element().sendData(zipCodeText, zipCode);
        driver.element().sendData(mobileNumberText, mobileNumber);

        return this;
    }

    @Step("Clicking Submit Button")
    public SignupPage clickCreateAccountButton()
    {
        driver.element().clickButton(submitButton);
        LogsUtil.info("Submit Button Clicked.");
        return this;
    }

    @Step("Returning to Home Page ")
    public HomePage clickContinueButton()
    {
        driver.element().clickButton(continueButton);
        return new HomePage(driver);
    }

    //Validation
    @Step("Verifying Account Registration")
    public SignupPage verifySuccessfulRegistration()
    {
        driver.verification().isElementVisible(accountCreatedMessage);
        LogsUtil.info("Account Created.");
        return this;
    }

}
