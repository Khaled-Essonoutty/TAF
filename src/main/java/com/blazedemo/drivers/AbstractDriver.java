package com.blazedemo.drivers;

import org.openqa.selenium.WebDriver;

import java.io.File;

public abstract class AbstractDriver {
    public abstract WebDriver createDriverInstance();
    protected File haramBlurExtension = new File("src/main/resources/extensions/HaramBlur.crx");
}
