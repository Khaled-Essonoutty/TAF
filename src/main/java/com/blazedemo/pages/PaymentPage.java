package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.actions.Waits;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    //Variables
    private final GUIDriver driver;
    private final String paymentPageEndPoint = "/payment";


    public PaymentPage(GUIDriver driver) {
        this.driver = driver;

    }

    //Locators
    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cvcNumber = By.name("cvc");
    private final By expiryMonth = By.name("expiry_month");
    private final By expiryYear = By.name("expiry_year");
    private final By submit = By.id("submit");

    private final By congratulationsMessage = By.xpath("//p[contains(text(),'Congratulations')]");
    private final By invoiceDownloadButton = By.xpath("//p[contains(text(),'Congratulations')]//following::a");
    //Actions
    @Step("Navigating to Payment Page")
    public PaymentPage navigate() {
        driver.browser().navigateToURL(PropertiesFileReader.getPropertyValue("baseUrlWeb") + paymentPageEndPoint);
        return this;
    }

    @Step("Fill user data for payment")
    public PaymentPage enterUserCardData(String nameOnCard, String cardNumber, String cvcNumber,
                                        String expiryMonth, String expiryYear) {
        driver.element()
                .sendData(nameOnCard, this.nameOnCard)
                .sendData(cardNumber, this.cardNumber)
                .sendData(cvcNumber, this.cvcNumber)
                .sendData(expiryMonth, this.expiryMonth)
                .sendData(expiryYear, this.expiryYear)
                .clickButton(submit);
        return this;
    }

    @Step("Download invoice file.")
    public PaymentPage downloadInvoice()
    {
        driver.element().clickButton(invoiceDownloadButton);
        return this;
    }

    //Validation
    @Step("Verifying Payment")
    public PaymentPage verifySuccessfulPayment(String expectedMessage){
        driver.verification().Equals(driver.element().getData(congratulationsMessage), expectedMessage, "Unsuccessful Payment.");
        return this;
    }

    @Step("Verifying downloading invoice")
    public PaymentPage verifyDownloadingInvoice(String fileName)
    {
        driver.verification().verifyFileDownload(fileName, "File is not downloaded");
        return this;
    }
}
