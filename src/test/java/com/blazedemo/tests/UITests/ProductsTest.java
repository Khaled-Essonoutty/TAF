package com.blazedemo.tests.UITests;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.listeners.TestNGListeners;
import com.blazedemo.pages.ProductsPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.dataReader.JsonReader;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestNGListeners.class)
@UITest
public class ProductsTest extends BaseTest {

    //Variables

    //Tests
    @Test
    public void searchProductWithoutLoginTC()
    {
        new ProductsPage(driver).navigate()
                .searchProduct(testData.getData("product1.name"))
                .validateProductDetails(testData.getData("product1.name"), testData.getData("product1.price"));
    }

    @Test
    public void addProductToCartWithoutLoginTC()
    {
        new ProductsPage(driver).navigate()
                .addItemToCart(testData.getData("product1.name"))
                .verifyAddProductToCartMessage(testData.getData("messages.product-added"));
    }


    //Configurations
    @BeforeClass
    public void setup()
    {
        driver = new GUIDriver();
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb"));
        //driver.browser().closeExtensionWindow();
        testData = new JsonReader("products-data");
    }

    @AfterClass
    public void tearDown()
    {
        driver.quite();
    }
}
