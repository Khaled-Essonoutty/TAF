package com.blazedemo.tests.UITests;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.ProductsPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.dataReader.JsonReader;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@UITest
public class CartTest extends BaseTest {
    //Variables

    //Tests
    @Test
    public void validateProductInfoInCartWithoutLoginTC()
    {
        new ProductsPage(driver)
                .navigate()
                .addItemToCart(testData.getData("product1.name"))
                .verifyAddProductToCartMessage(testData.getData("messages.product-added"))
                .clickOnViewCart()
                .validateProductInfoInCart(testData.getData("product1.name"),
                        testData.getData("product1.price"),
                        testData.getData("product1.total-price"));
    }

    //Configurations
    @BeforeClass
    public void preConditions()
    {
        driver = new GUIDriver();
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb"));
        driver.browser().closeExtensionWindow();
        testData = new JsonReader("cart-page");
    }

    @AfterClass
    public void tearDown()
    {
        driver.quite();
    }
}
