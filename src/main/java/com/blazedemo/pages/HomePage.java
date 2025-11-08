package com.blazedemo.pages;

import com.blazedemo.drivers.GUIDriver;
import org.openqa.selenium.By;

public class HomePage {
    //Variables
    private final GUIDriver driver;

    //Locators
    private final By userNameLabel = By.xpath("//a /b");

    //constructor
    public HomePage(GUIDriver driver) {
        this.driver = driver;
    }

    //Actions



}
