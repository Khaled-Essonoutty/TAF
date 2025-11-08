package com.blazedemo.utils.dataReader;

import com.blazedemo.utils.logs.LogsUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class PropertiesFileReader {
    private static final String propertiesPath = "src/main/resources/";

    public static Properties loadProperties()
    {
        try {
            Properties properties = new Properties();
            Collection<File> propertiesFilesList;
            propertiesFilesList = FileUtils.listFiles(new File(propertiesPath), new String[]{"properties"}, true);
            propertiesFilesList.forEach(propertyFile -> {
                try {
                    properties.load(new FileInputStream(propertyFile));
                } catch (IOException ioe) {
                    LogsUtil.error(ioe.getMessage());
                }
                properties.putAll(System.getProperties());
                System.getProperties().putAll(properties);
            });
            //LogsUtil.info("Properties loaded successfully");
            return properties;
        }catch (Exception e)
        {
            LogsUtil.warn("Failed to load the properties.");
            return null;
        }
    }

    public static String getPropertyValue(String key)
    {
        try{
            String property = System.getProperty(key);
            LogsUtil.info("Getting Property key: " + property);
            return property;
        }
        catch (Exception e)
        {
            LogsUtil.error("Failed to get property for key: " + key);
            return " ";
        }
    }
}
