package com.blazedemo.apis;

import com.blazedemo.utils.dataReader.PropertiesFileReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static final String baseUrlApi = PropertiesFileReader.getPropertyValue("baseUrlApi");

    public static RequestSpecification getUserManagementSpecification(Map<String, ?> formParams)
    {
        return new RequestSpecBuilder().setBaseUri(baseUrlApi)
                .setContentType(ContentType.URLENC)
                .addFormParams(formParams)
                .build();
    }

}
