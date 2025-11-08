package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    //Variables
    private final GUIDriver driver;
    private final String checkoutPageEndPoint = "/checkout";


    //Constructor
    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Locators
    private final By deliveryName = By.xpath("//ul[@id='address_delivery']//li[@class='address_firstname address_lastname']");
    private final By deliveryCompany = By.xpath("//ul[@id='address_delivery']//li[@class='address_address1 address_address2'][1]");
    private final By deliveryAddress1 = By.xpath("//ul[@id='address_delivery']//li[@class='address_address1 address_address2'][2]");
    private final By deliveryAddress2 = By.xpath("//ul[@id='address_delivery']//li[@class='address_address1 address_address2'][3]");
    private final By deliveryStateCityZipCode = By.xpath("//ul[@id='address_delivery']//li[@class='address_city address_state_name address_postcode']");
    private final By deliveryCountry = By.xpath("//ul[@id='address_delivery']//li[@class='address_country_name']");
    private final By deliveryPhoneNumber = By.xpath("//ul[@id='address_delivery']//li[@class='address_phone']");

    private final By billingName = By.xpath("//ul[@id='address_invoice']//li[@class='address_firstname address_lastname']");
    private final By billingCompany = By.xpath("//ul[@id='address_invoice']//li[@class='address_address1 address_address2'][1]");
    private final By billingAddress1 = By.xpath("//ul[@id='address_invoice']//li[@class='address_address1 address_address2'][2]");
    private final By billingAddress2 = By.xpath("//ul[@id='address_invoice']//li[@class='address_address1 address_address2'][3]");
    private final By billingStateCityZipCode = By.xpath("//ul[@id='address_invoice']//li[@class='address_city address_state_name address_postcode']");
    private final By billingCountry = By.xpath("//ul[@id='address_invoice']//li[@class='address_country_name']");
    private final By billingPhoneNumber = By.xpath("//ul[@id='address_invoice']//li[@class='address_phone']");

    private final By totalCartPrice = By.xpath("//h4[.='Total Amount']//following::td//p");
    private final By commentArea = By.cssSelector(".form-control");
    private final By placeOrderButton = By.cssSelector(".check_out");

    //Dynamic Locators
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

    //Actions
    @Step("Typing comment: {comment}")
    public CheckoutPage typeComment(String comment) {
        driver.element().sendData(comment, commentArea);
        return this;
    }

    @Step("Placing Order.")
    public PaymentPage placeOrder() {
        driver.element().clickButton(placeOrderButton);
        return new PaymentPage(driver);
    }

    //Validations
    @Step("Validating Delivery Info")
    public CheckoutPage validateDeliveryInfo(String gender, String firstName, String lastName, String company,
                                             String address1, String address2, String city, String state, String zipCode,
                                             String country, String phoneNumber) {
        driver.validation()
                .Equals(driver.element().getData(deliveryName), gender + ". " + firstName + " " + lastName, "Delivery Name is not matched.")
                .Equals(driver.element().getData(deliveryCompany), company, "Delivery Company name is not matched.")
                .Equals(driver.element().getData(deliveryAddress1), address1, "Delivery Address 1 is not matched.")
                .Equals(driver.element().getData(deliveryAddress2), address2, "Delivery Address 2 is not matched.")
                .Equals(driver.element().getData(deliveryStateCityZipCode), (zipCode + " " + city + " " + state), "Delivery City State Zipcode are not matched.")
                .Equals(driver.element().getData(deliveryCountry), country, "Delivery Country is not matched.")
                .Equals(driver.element().getData(deliveryPhoneNumber), phoneNumber, "Delivery Phone Number is not matched.");
        return this;
    }

    @Step("Validating Delivery Info")
    public CheckoutPage validateBillingInfo(String gender, String firstName, String lastName, String company,
                                            String address1, String address2, String city, String state, String zipCode,
                                            String country, String phoneNumber) {
        driver.validation()
                .Equals(driver.element().getData(billingName), gender + ". " + firstName + " " + lastName, "Billing Name is not matched.")
                .Equals(driver.element().getData(billingCompany), company, "Billing Company name is not matched.")
                .Equals(driver.element().getData(billingAddress1), address1, "Billing Address 1 is not matched.")
                .Equals(driver.element().getData(billingAddress2), address2, "Billing Address 2 is not matched.")
                .Equals(driver.element().getData(billingStateCityZipCode), (zipCode + " " + city + " " + state), "Billing City State Zipcode are not matched.")
                .Equals(driver.element().getData(billingCountry), country, "Billing Country is not matched.")
                .Equals(driver.element().getData(billingPhoneNumber), phoneNumber, "Billing Phone Number is not matched.");
        return this;
    }
}
