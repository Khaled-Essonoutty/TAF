package com.blazedemo.utils.actions;

import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final Waits wait;

    public AlertActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new Waits(driver);
    }

    public void acceptAlert()
    {
        wait.fluentWait().until(d -> {
            try {
                d.switchTo().alert().accept();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        });
    }

    public void dismissAlert()
    {
        wait.fluentWait().until(d -> {
            try {
                driver.switchTo().alert().dismiss();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        });
    }

    public String getAlertText()
    {
        return wait.fluentWait().until(d -> {
            try {
                String alertText = d.switchTo().alert().getText();
                return !alertText.isEmpty()? alertText:null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        });
    }

    public void sentTextToAlert(String text)
    {
        wait.fluentWait().until(d-> {
            try {
                d.switchTo().alert().sendKeys(text);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        });
    }
}
