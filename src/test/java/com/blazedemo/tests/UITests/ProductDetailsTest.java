package com.blazedemo.tests.UITests;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.ProductDetails;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.dataReader.JsonReader;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@UITest
public class ProductDetailsTest extends BaseTest {
    //Variables

    //Tests
    @Test
    public void verifyProductInformationWithoutLoginTC()
    {
        new ProductDetails(driver).navigate().validateProductInfo(testData.getData("product1.name")
                , testData.getData("product1.price"));
    }

    @Test
    public void verifyReviewMessageWithoutLoginTC()
    {
        new ProductDetails(driver)
                .navigate()
                .writeReview(testData.getData("review.name")
                        , testData.getData("review.email")
                        , testData.getData("review.review-message"))
                .verifyReview(testData.getData("messages.alert-success-review-message"));
    }

    //Configurations
    @BeforeClass
    public void beforeClass()
    {
        driver = new GUIDriver();
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb"));
        driver.browser().closeExtensionWindow();
        testData = new JsonReader("product-details");
    }

    @AfterClass
    public void tearDown()
    {
        driver.quite();
    }
}
