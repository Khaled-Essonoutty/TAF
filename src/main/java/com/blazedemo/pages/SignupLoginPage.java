package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    //Variables
    private final GUIDriver driver;
    private final String loginPageEndpoint = "/login";
    //Locators
    By loginEmailAddress = By.xpath("//input[@data-qa='login-email']");
    By loginPassword = By.xpath("//input[@data-qa='login-password']");
    By loginButton = By.xpath("//button[@data-qa='login-button']");

    By signupName = By.xpath(("//input[@data-qa='signup-name']"));
    By signupEmailAddress = By.xpath("//input[@data-qa='signup-email']");
    By signupButton = By.xpath("//button[@data-qa='signup-button']");


    By signupHeader = By.cssSelector(".signup-form h2");
    By loginErrorMessage = By.cssSelector(".login-form p");
    By signupErrorMessage = By.cssSelector(".signup-form p");



    //Constructors
    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step("Navigating")
    public SignupLoginPage navigate()
    {
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb") + loginPageEndpoint);
        return this;
    }

    @Step("Entering Login Email Address")
    private SignupLoginPage enterLoginEmailAddress(String emailAddress) {
        LogsUtil.info("Entering Login Email Address: ", emailAddress);
        driver.element().sendData(emailAddress, loginEmailAddress);
        return this;
    }

    @Step("Entering Password")
    private SignupLoginPage enterPassword(String password) {
        LogsUtil.info("Entering Password: ", password);
        driver.element().sendData(password, loginPassword);
        return this;
    }

    @Step("Clicking Login Button")
    private SignupLoginPage clickLogin() {
        LogsUtil.info("Clicking Login Button");
        driver.element().clickButton(loginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Logging")
    public SignupLoginPage login(String emailAddress, String password) {
        enterLoginEmailAddress(emailAddress).enterPassword(password).clickLogin();
        return this;
    }

    @Step("Entering Name")
    private SignupLoginPage enterName(String name) {
        LogsUtil.info("Entering Name: ", name);
        driver.element().sendData(name, signupName);
        return this;
    }

    @Step("Entering Signup Email")
    private SignupLoginPage enterSignupEmail(String emailAddress) {
        LogsUtil.info("Entering Signup Email: ", emailAddress);
        driver.element().sendData(emailAddress, signupEmailAddress);
        return this;
    }

    @Step("Clicking Signup Button")
    private SignupPage clickSignupButton() {
        LogsUtil.info("Clicking Login Button");
        driver.element().clickButton(signupButton);
        return new SignupPage(driver);
    }

    @Step("Signup")
    public SignupPage signup(String name, String emailAddress) {
        enterName(name).enterSignupEmail(emailAddress).clickSignupButton();
        return new SignupPage(driver);
    }

    //Validations
    public SignupLoginPage verifyLoginPageNavigation()
    {
        LogsUtil.info("Verifying Login Page Navigation");
        driver.verification().isElementVisible(signupHeader);
        return this;
    }

    @Step("Verifying unsuccessful login scenario {expectedErrorMessage}")
    public SignupLoginPage verifyingLoginErrorMessage(String expectedErrorMessage)
    {
        LogsUtil.info("Verifying unsuccessful login scenario");
        String actualErrorMessage = driver.element().getData(loginErrorMessage);
        driver.verification().Equals(actualErrorMessage, expectedErrorMessage, "Error Messages doesn't match");
        return this;
    }

    public SignupLoginPage verifyingSignupErrorMessage(String expectedErrorMessage)
    {
        LogsUtil.info("Verifying Registering with already registered data");
        String actualErrorMessage = driver.element().getData(signupErrorMessage);
        driver.verification().Equals(actualErrorMessage, expectedErrorMessage, "Error Message doesn't match");
        return this;
    }




}
