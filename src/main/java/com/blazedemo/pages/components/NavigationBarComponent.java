package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.*;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ws.schild.jave.encode.VideoAttributes;

public class NavigationBarComponent {

    //Variables
    private final GUIDriver driver;

    //Locators
    private final By homePageButton = By.className("fa-home");
    private final By productsButton = By.linkText(" Products");
    private final By cartButton = By.className("card_travel");
    private final By signupLoginButton = By.linkText(" Signup / Login");
    private final By videoTutorialButton = By.xpath("//a[.=' Video Tutorials']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By homePageLabel = By.xpath("//div[@class='carousel-inner']//h1");
    private final By user = By.xpath("//a /b");


    //Constructor
    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    //Actions
    public NavigationBarComponent navigate()
    {
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb"));
        return this;
    }

    @Step("Click On Home button")
    public NavigationBarComponent clickHomePageButton()
    {
        driver.element().findElement(homePageButton).click();
        return this;
    }
    @Step("Click On Products button")
    public ProductsPage clickProductsButton()
    {
        driver.element().findElement(productsButton).click();
        return new ProductsPage(driver);
    }
    @Step("Click On Cart button")
    public CartPage clickCartButton()
    {
        driver.element().findElement(cartButton).click();
        return new CartPage(driver);
    }
    @Step("Click On Signup/Login button")
    public SignupLoginPage clickSignupLoginButton()
    {
        driver.element().findElement(signupLoginButton).click();
        return new SignupLoginPage(driver);
    }
    @Step("Click On Video Tutorial button")
    public VideoTutorialPage clickVideoTutorialButton()
    {
        driver.element().findElement(videoTutorialButton).click();
        return new VideoTutorialPage(driver);
    }
    @Step("Click On Contact Us button")
    public ContactUsPage clickContactUsButton(){
        driver.element().findElement(contactUsButton).click();
        return new ContactUsPage(driver);
    }

    //Validation
    public void validateHomePageLabel()
    {
        LogsUtil.info("Validating Home Page Label Existence.");
        driver.verification().isElementVisible(homePageLabel);
    }

    public void validateUserName(String expectedUserName)
    {
        LogsUtil.info("Validating User Name.");
        String actualUserName = driver.element().findElement(user).getText();
        driver.verification().Equals(actualUserName, expectedUserName, "User Name doesn't match.");
    }
}
