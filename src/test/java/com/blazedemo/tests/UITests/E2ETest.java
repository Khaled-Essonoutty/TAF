package com.blazedemo.tests.UITests;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.CartPage;
import com.blazedemo.pages.PaymentPage;
import com.blazedemo.pages.ProductsPage;
import com.blazedemo.pages.SignupLoginPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@UITest
public class E2ETest extends BaseTest {
    //Variables
    String timeStamp = TimeManager.getSimpleTimestamp();

    //Tests
    @Test
    public void registerNewAccountTC() {
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

    @Test(dependsOnMethods = "registerNewAccountTC")
    public void loginTC() {
        new SignupLoginPage(driver)
                .navigate()
                .login(testData.getData("email") + timeStamp + "@gmail.com", testData.getData("password"));
    }

    @Test(dependsOnMethods = {"registerNewAccountTC", "loginTC"})
    public void addProductToCartTC() {
        new ProductsPage(driver).navigate()
                .addItemToCart(testData.getData("product1.name"))
                .verifyAddProductToCartMessage(testData.getData("messages.product-added"))
                .clickOnViewCart();
    }

    @Test(dependsOnMethods = {"registerNewAccountTC", "loginTC", "addProductToCartTC"})
    public void checkoutTC() {
        new CartPage(driver).proceedToCheckout()
                .validateBillingInfo(testData.getData("titleMale"),
                        testData.getData("firstName"),
                        testData.getData("lastName"),
                        testData.getData("companyName"),
                        testData.getData("address1"),
                        testData.getData("address2"),
                        testData.getData("city"),
                        testData.getData("state"),
                        testData.getData("zipcode"),
                        testData.getData("country"),
                        testData.getData("mobileNumber"))
                .validateDeliveryInfo(testData.getData("titleMale"),
                        testData.getData("firstName"),
                        testData.getData("lastName"),
                        testData.getData("companyName"),
                        testData.getData("address1"),
                        testData.getData("address2"),
                        testData.getData("city"),
                        testData.getData("state"),
                        testData.getData("zipcode"),
                        testData.getData("country"),
                        testData.getData("mobileNumber"))
                .placeOrder();

    }

    @Test(dependsOnMethods = {"registerNewAccountTC", "loginTC", "addProductToCartTC", "checkoutTC"})
    public void verifyPaymentTC() {
        new PaymentPage(driver)
                .enterUserCardData(testData.getData("card_data.nameOnCard"),
                        testData.getData("card_data.cardNumber"),
                        testData.getData("card_data.cvc"),
                        testData.getData("card_data.expiryMonth"),
                        testData.getData("card_data.expiryYear"))
                .verifySuccessfulPayment(testData.getData("messages.congratulations_message"));
    }

    @Test(dependsOnMethods = {"registerNewAccountTC", "loginTC", "addProductToCartTC", "checkoutTC", "verifyPaymentTC"})
    public void deleteAccountTC() {
        new UserManagementAPI()
                .deleteUserAccount(testData.getData("email") + timeStamp + "@gmail.com",
                        testData.getData("password"))
                .verifyDeletingUserAccountSuccessfully();
    }

    //Configurations
    @BeforeClass
    public void preConditions() {
        driver = new GUIDriver();
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb"));
        //driver.browser().closeExtensionWindow();
        testData = new JsonReader("E2E-data");
    }

    @AfterClass
    public void tearDown() {
        driver.quite();
    }
}
