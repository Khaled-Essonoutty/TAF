package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetails {

    //Varibles
    private final GUIDriver driver;
    private final String productDetailsPageEndPoint = "/product_details/2";

    //Locators
    private final By productName = By.cssSelector(".product-information > h2");
    private final By productPrice = By.cssSelector(".product-information > span > span");
    private final By productQuantity = By.id("quantity");
    private final By addToCartButton = By.cssSelector("span > .btn-default");
    /*
     * To review
     * */
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By review = By.id("review");
    private final By reviewButton = By.id("button-review");
    private final By alertSuccessMessage = By.cssSelector(".alert-success > span");

    //Constructor
    public ProductDetails(GUIDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step("Navigating to Product Details Page")
    public ProductDetails navigate() {
        driver.browser()
                .navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb") + productDetailsPageEndPoint);
        return this;
    }

    @Step("Writing review")
    public ProductDetails writeReview(String name, String email, String reviewMessage) {
        driver.element().sendData(name, this.name)
                .sendData(email, this.email)
                .sendData(reviewMessage, this.review)
                .clickButton(reviewButton);
        return this;
    }

    //Validation
    @Step("Verifying Product Information")
    public ProductDetails validateProductInfo(String expectedProductName, String expectedProductPrice) {
        String actualProductName = driver.element().getData(productName);
        String actualProductPrice = driver.element().getData(productPrice);
        LogsUtil.info("Validating Product Info: Actual Product Name:", actualProductName, "- Actual Price:", actualProductPrice);
        driver.validation().Equals(actualProductName,expectedProductName,"Product Names don't match");
        driver.validation().Equals(actualProductPrice,expectedProductPrice,"Product Prices don't match");
        return this;
    }
    @Step("Verifying Alert Success Message")
    public ProductDetails verifyReview(String expectedAlertSuccessMessage)
    {
        String actualAlertSuccessMessage = driver.element().getData(alertSuccessMessage);
        LogsUtil.info("Verifying Alert message Actual:",actualAlertSuccessMessage,"- Expected:",expectedAlertSuccessMessage);
        driver.verification().Equals(actualAlertSuccessMessage,expectedAlertSuccessMessage,"Messages doesn't match");
        return this;
    }
}
