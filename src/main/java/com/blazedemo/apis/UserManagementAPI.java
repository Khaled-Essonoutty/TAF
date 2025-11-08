package com.blazedemo.apis;

import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import com.blazedemo.utils.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;



public class UserManagementAPI {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;

    public UserManagementAPI()
    {
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //endpoints
    private static final String createAccountEndPoint = "/createAccount";
    private static final String deleteAccountEndPoint = "/deleteAccount";

    //API methods
    @Step("Creating new user account with full details")
    public UserManagementAPI createRegisterUserAccount(String name, String email, String pass,String title,String birth_date, String birth_month,String birth_year, String firstName, String lastName
            , String company,String address1, String address2,String country,String zipcode,String state,String city,String mobile_number)
    {
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", pass);
        formParams.put("title", title);
        formParams.put("birth_date", birth_date);
        formParams.put("birth_month", birth_month);
        formParams.put("birth_year", birth_year);
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobile_number);
        response = requestSpecification.spec(Builder.getUserManagementSpecification(formParams)).post(createAccountEndPoint);
        LogsUtil.info(response.asPrettyString());
        return this;

    }

    @Step("Creating new user account with minimal data")
    public UserManagementAPI createRegisterUserAccount(String name, String email, String pass, String firstName, String lastName
            )
    {
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", pass);
        formParams.put("title", "title");
        formParams.put("birth_date", "birth_date");
        formParams.put("birth_month", "birth_month");
        formParams.put("birth_year", "birth_year");
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "india");
        formParams.put("zipcode", "zipcode");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("mobile_number", "mobile_number");
        response = requestSpecification.spec(Builder.getUserManagementSpecification(formParams)).post(createAccountEndPoint);
        LogsUtil.info(response.asPrettyString());
        return this;

    }

    public UserManagementAPI deleteUserAccount(String email, String password)
    {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementSpecification(formParams)).delete(deleteAccountEndPoint);
        LogsUtil.info(response.asPrettyString());
        return this;
    }

    //Validations
    @Step("Verify user creation")
    public UserManagementAPI verifyUserCreatedSuccessfully()
    {
        verification.Equals(response.jsonPath().get("message"), "User created!",
                "User is not created Successfully.");
        LogsUtil.info("User is created successfully.");
        return this;
    }

    @Step("Verify deleting user account")
    public UserManagementAPI verifyDeletingUserAccountSuccessfully()
    {
        verification.Equals(response.jsonPath().get("message"), "Account deleted!",
                "Account is not deleted.");
        return this;
    }

}
