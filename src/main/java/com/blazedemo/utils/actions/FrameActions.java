package com.blazedemo.utils.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    private final Waits wait;
    private final WebDriver driver;

    public FrameActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new Waits(driver);
    }

    /*
    * Switches to frame to interact with its elements
    * @param indexOfTheFrame
    * */
    public void switchToFrameByIndex(int index)
    {
        wait.fluentWait().until(d->{
            try {
                driver.switchTo().frame(index);
                return true;
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        });
    }

    public void switchToFrameByNameOrId(String nameOrId)
    {
        wait.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(nameOrId);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        });
    }

    public void switchToFrameByWebElement(By locator){
        wait.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(d.findElement(locator));
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        });
    }

    public void switchToDefaultContent()
    {
        wait.fluentWait().until(d -> {
            try {
                driver.switchTo().defaultContent();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        });
    }

}
