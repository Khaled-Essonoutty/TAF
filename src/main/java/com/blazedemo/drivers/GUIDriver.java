package com.blazedemo.drivers;

import com.blazedemo.utils.actions.AlertActions;
import com.blazedemo.utils.actions.BrowserActions;
import com.blazedemo.utils.actions.ElementActions;
import com.blazedemo.utils.actions.FrameActions;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import com.blazedemo.utils.validations.Validation;
import com.blazedemo.utils.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver() {
        LogsUtil.info("Getting Browser: ", PropertiesFileReader.getPropertyValue("browserType"));
        //System.out.println(PropertiesFileReader.getPropertyValue("browserType"));
        WebDriver driver = ThreadGuard.protect(getDriver(PropertiesFileReader.getPropertyValue("browserType"))
                .createDriverInstance());
        driverThreadLocal.set(driver);
    }

    private AbstractDriver getDriver(String browser) {
        /*
         * This driver is used to identify which driver you will initialize
         * */
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeFactory();
            case "edge" -> new EdgeFactory();
            default -> throw new IllegalArgumentException("Wrong Browser name: " + browser);
        };
    }

    public ElementActions element() {
        return new ElementActions(getDriver());
    }
    public BrowserActions browser() {
        return new BrowserActions(getDriver());
    }
    public FrameActions frame() {
        return new FrameActions(getDriver());
    }
    public AlertActions alert() {
        return new AlertActions(getDriver());
    }
    //soft assertions
    public Validation validation() {
        return new Validation(getDriver());
    }
    // hard assertions
    public Verification verification() {
        return new Verification(getDriver());
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public void quite() {
        driverThreadLocal.get().quit();
    }
}
