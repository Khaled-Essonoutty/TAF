package com.blazedemo.drivers;

import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class EdgeFactory extends AbstractDriver {
    private final String remoteHost = PropertiesFileReader.getPropertyValue("remoteHost");
    private final String remotePort = PropertiesFileReader.getPropertyValue("remotePort");

    @Override
    public WebDriver createDriverInstance() {

        //System.setProperty("webdriver.edge.driver", "C:\\Users\\Khaled Essonoutty\\Downloads\\msedgedriver.exe");
        String executionType = PropertiesFileReader.getPropertyValue("executionType");
        if (executionType.equalsIgnoreCase("remote-headless")) {
            try {
                return new RemoteWebDriver(new URI("http://" + remoteHost + ":" + remotePort + "/wd/hub").toURL(), getOptions());
            } catch (Exception e) {
                LogsUtil.error("Failed to initialize remote driver", e.getMessage());
                throw new RuntimeException("Failed to initialize remote driver: " + e.getMessage());
            }
        } else if (executionType.equalsIgnoreCase("Headless") || executionType.equalsIgnoreCase("local")) {
            return new EdgeDriver(getOptions());
        } else {
            LogsUtil.error("Wrong Execution Type.");
            throw new IllegalArgumentException("Wrong Execution Type.");
        }
    }

    private EdgeOptions getOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<>();
        String userDir = System.getProperty("user.dir");
        String downloadPath = userDir + "\\src\\test\\resources\\downloads";
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", prefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS, true);
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if(PropertiesFileReader.getPropertyValue("extension").equals("enabled"))
            options.addExtensions(haramBlurExtension);
        switch (PropertiesFileReader.getPropertyValue("executionType")) {
            case "Headless" -> options.addArguments("--headless=new");
            case "Remote" -> {
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.addArguments("--headless=new");
            }
        }
        return options;
    }
}
