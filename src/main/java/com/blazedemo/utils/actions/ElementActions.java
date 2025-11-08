package com.blazedemo.utils.actions;

import com.blazedemo.utils.logs.LogsUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {
    private final WebDriver driver;
    private final Waits wait;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new Waits(driver);
    }

    public ElementActions sendData(String data, By locator) {
        //driver.findElement(locator).sendKeys(data);
        LogsUtil.info("Writing " + data + " in " + locator.toString());
        wait.fluentWait().until(driver1 -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(data);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

    public String getData(By locator) {
        LogsUtil.info("getting data from " + locator.toString());
        return wait.fluentWait()
                .until(d -> {
                    try {
                        WebElement element = driver.findElement(locator);
                        scrollToElementJS(locator);
                        String msg = element.getText();
                        return !msg.isEmpty() ? msg : null;
                    } catch (Exception e) {
                        return null;
                    }
                });
    }

    public ElementActions clickButton(By locator) {
        LogsUtil.info("Clicking on" + locator.toString());
        wait.fluentWait().until(d ->
        {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElementJS(locator);
                element.click();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

    public ElementActions selectFromDropDown(String value, By locator) {
        LogsUtil.info("Selecting from DropDown " + locator.toString());
        wait.fluentWait().until(driver1 -> {
            try {
                WebElement element = driver1.findElement(locator);
                scrollToElementJS(locator);
                Select select = new Select(element);
                select.selectByVisibleText(value);
                LogsUtil.info("Selected " + value + "from DropDown.");
                return true;
            } catch (Exception e) {
                LogsUtil.error("Couldn't Select From Drop Down.");
                return false;
            }
        });
        return this;
    }

    public ElementActions uploadFile(String filePath, By locator) {
        String path = System.getProperty("user.dir") + File.separator + filePath;
        LogsUtil.info("Uploading " + filePath + " to " + locator.toString());
        wait.fluentWait().until(d -> {
            try {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(path);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        });
        return this;
    }

    public ElementActions scrollToElementJS(By locator) {
        LogsUtil.info("Scrolling To element: " + locator.toString());
        ((JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""", findElement(locator));
        return this;
    }

    public ElementActions hover(By locator) {
        wait.fluentWait().until(d -> {
            try {
                WebElement element = findElement(locator);
                scrollToElementJS(locator);
                Point initialLocation = element.getLocation();
                LogsUtil.info("Initial Location for the element: " + initialLocation);
                Point finalLocation = element.getLocation();
                LogsUtil.info("Final Location for the element: " + finalLocation);
                if(!finalLocation.equals(initialLocation))
                {
                    return false;
                }
                new Actions(driver).moveToElement(element).perform();
                LogsUtil.info("Hovered over the element: ", locator.toString());
                return true;
            } catch (Exception e) {
                LogsUtil.info("Couldn't hoover over the element: ", locator.toString());
                return false;
            }
        });
        return this;
    }

    public WebElement findElement(By locator) {
        LogsUtil.info("Finding element: " + locator.toString());
        return driver.findElement(locator);
    }
}
