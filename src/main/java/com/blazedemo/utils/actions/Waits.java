package com.blazedemo.utils.actions;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Waits {
    private final WebDriver driver;

    public Waits(WebDriver driver) {
        this.driver = driver;
    }

    public FluentWait<WebDriver> fluentWait()
    {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100L))
                .withMessage("Element doesn't exist")
                .ignoreAll(getExceptions());
    }

    private ArrayList<Class<? extends Exception>> getExceptions()
    {
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<Class<? extends Exception>>();
        exceptions.add(NoSuchElementException.class);
        exceptions.add(StaleElementReferenceException.class);
        exceptions.add(ElementNotInteractableException.class);
        exceptions.add(ElementClickInterceptedException.class);


        return exceptions;
    }
}
