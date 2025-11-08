package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private final GUIDriver driver;

    public CartPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Variables
    private final String cartPageEndPoint = "/view_cart";

    //Locators
    private final By checkoutButton = By.cssSelector(".check_out");

    private By productName(String productName) {
        return By.xpath("//td[@class= 'cart_description']//h4[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//td[@class= 'cart_description']//h4[.='" + productName + "']//following::td//p");
    }

    private By productQuantity(String productName) {
        return By.xpath("//td[@class= 'cart_description']//h4[.='" + productName + "']//following::td[@class='cart_quantity'][1]//button");
    }

    private By productTotalPrice(String productName) {
        return By.xpath("//td[@class= 'cart_description']//h4[.='" + productName + "']//following::td[@class='cart_total'][1]//p");
    }

    private By productDeleteButton(String productName) {
        return By.xpath("//td[@class= 'cart_description']//h4[.='" + productName + "']//following::td[@class='cart_delete'][1]//a");
    }

    //Actions
    @Step("Navigating")
    public CartPage navigate() {
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb") + cartPageEndPoint);
        return this;
    }

    @Step("Removing Product from cart.")
    public CartPage removeProductFromCart(String productName) {
        driver.element().clickButton(productDeleteButton(productName));
        return this;
    }

    @Step("Proceeding to checkout.")
    public CheckoutPage proceedToCheckout() {
        driver.element().clickButton(checkoutButton);
        return new CheckoutPage(driver);
    }

    //Validations
    public CartPage validateProductInfoInCart(String expectedName, String expectedPrice, String expectedTotalPrice) {
        String actualName = driver.element().getData(productName(expectedName));
        String actualPrice = driver.element().getData(productPrice(expectedName));
        String actualTotalPrice = driver.element().getData(productTotalPrice(expectedName));
        /*
         * This conversion is not useful in this case.
         * We are going to need it when we have a coupon for example, and we need to know the new price.
         * */
        int actualTotalPriceValue = Integer.parseInt(actualTotalPrice.substring(4));
        int expectedTotalPriceValue = Integer.parseInt(expectedTotalPrice.substring(4));

        driver.validation().Equals(actualName, expectedName, "Produce Names don't match.");
        driver.validation().Equals(actualPrice, expectedPrice, "The prices is not equal.");
        driver.validation().Equals(actualTotalPrice, expectedTotalPrice, "The total price is not matched.");
        LogsUtil.info("Validating Product Info");
        return this;
    }
}
