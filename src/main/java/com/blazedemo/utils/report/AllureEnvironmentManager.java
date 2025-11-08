package com.blazedemo.utils.report;

import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {

    public static void setEnvironmentVariables() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", PropertiesFileReader.getPropertyValue("os.name"))
                        .put("Java version:", PropertiesFileReader.getPropertyValue("java.runtime.version"))
                        .put("Browser", PropertiesFileReader.getPropertyValue("browserType"))
                        .put("Execution Type", PropertiesFileReader.getPropertyValue("executionType"))
                        .put("URL", PropertiesFileReader.getPropertyValue("baseUrlWeb"))
                        .build(), String.valueOf(AllureConstants.RESULTS_FOLDER) + File.separator
        );
        LogsUtil.info("Allure environment variables set.");
        AllureBinaryManager.downloadAndExtract();
    }
}

