package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage {

    //Variables
    private final GUIDriver driver;
    private final String productsPageEndPoint = "/products";

    //Constructor
    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Locators
    private final By searchProductBar = By.id("search_product");
    private final By searchProductButton = By.id("submit_search");
    private final By itemAddedLabel = By.cssSelector(".modal-body > p:nth-of-type(1)");
    private final By continueShoppingButton = By.cssSelector(".modal-footer > button:nth-of-type(1)");
    private final By viewCart = By.xpath("//a[.='View Cart']");


    private By productName(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='overlay-content']//p[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='overlay-content']//p[.='" + productName + "']//preceding-sibling::h2");
    }

    private By addToCartButton(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='overlay-content']//p[.='" + productName + "']//following::a");
    }

    private By viewProduct(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='overlay-content']//p[.='" + productName + "']//following::div[@class='choose'][1]");
    }

    //Actions
    @Step("Navigating to Products page")
    public ProductsPage navigate()
    {
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb") + productsPageEndPoint);
        return this;
    }
    @Step("Adding Item to the cart: {productName}")
    public ProductsPage addItemToCart(String productName)
    {
        driver.element().hover(productName(productName))
                .clickButton(addToCartButton(productName));
        return this;
    }
    @Step("Searching for product")
    public ProductsPage searchProduct(String productName)
    {
        driver.element().sendData(productName, searchProductBar)
                .clickButton(searchProductButton);
        return this;
    }

    @Step("Click on view product")
    public ProductDetails clickOnViewProductDetails(String productName)
    {
        driver.element().clickButton(viewProduct(productName));
        return new ProductDetails(driver);
    }

    public ProductsPage clickOnContinueShoppingButton()
    {
        driver.element().clickButton(continueShoppingButton);
        return this;
    }

    public CartPage clickOnViewCart()
    {
        driver.element().clickButton(viewCart);
        return new CartPage(driver);
    }

    //Validations
    @Step("Verifying add to cart message: {expectedAddToCartMessage}")
    public ProductsPage verifyAddProductToCartMessage(String expectedAddToCartMessage)
    {
        String actualAddToCartMessage = driver.element().getData(itemAddedLabel);
        driver.verification().Equals(actualAddToCartMessage, expectedAddToCartMessage, "Add product message does not match");
        LogsUtil.info("Add to cart product verified");
        return this;
    }

    @Step("Validate product details for {productName} with price {productPrice}")
    public ProductsPage validateProductDetails(String productName, String productPrice) {
        String actualProductName = driver.element().hover(productName(productName)).getData(productName(productName));
        String actualProductPrice = driver.element().hover(productName(productName)).getData(this.productPrice(productName));
        LogsUtil.info("Validating product details for: " + actualProductName, " with price: " + actualProductPrice);
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        return this;
    }
}
