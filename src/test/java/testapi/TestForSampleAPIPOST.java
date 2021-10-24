package testapi;


import com.aventstack.extentreports.Status;
import libapihelper.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import resources.InputRequest.TestData;
import java.io.IOException;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestForSampleAPIPOST {
    RequestBuilder requestBuilder;
    RestResponse restResponse;
    String POSTUsersEndpoint, GETUserEndpoint,endpointURL,resourcePath,requestBody,exceptedEmail,jsonStr;
    Properties properties;

    @BeforeSuite
    public void suiteSetup() throws IOException {
        properties = Helpers.loadProperties();
        POSTUsersEndpoint=System.getProperty("endpointURLPOST")+ System.getProperty("userURIPOST");
    }
   // @Test (priority = 1,description = "To Create User with Valid Email")
    @Description ("Description : Testing Create User Endpoint the API whether success 200 and Exception 400")
    public void validateCreateUserEndpointWithValidExitingEmail() throws JSONException, IOException {

        ExtentTestManager.startTest("To Test if User Endpoint for Success Flow");
        jsonStr = TestData.ValidUserEmail();
        JSONObject jsonObj = new JSONObject(jsonStr);
        exceptedEmail = jsonObj.getString("email");

       // POSTUsersEndpoint=System.getProperty("endpointURL")+System.getProperty("userURI");

        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, jsonStr);
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==200){
            ExtentTestManager.getTest().log(Status.INFO, "This Test Case Validate " +
                    "Success response for Crate User flow  " +
                    " Excepted Response  "+POSTUsersEndpoint + "   Input Request  "+jsonStr);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Test Case Validate " +
                    "Success response for Crate User flow  " +
                    "Excepted Response  " + POSTUsersEndpoint   + "  Input Request " + jsonStr);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
        assertEquals(restResponse.getResponse().path("result.email"),exceptedEmail,"Email Id Mismatch ");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> Create User");
        // To check with Exiting Email ID
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==400){
            ExtentTestManager.getTest().log(Status.INFO, "This Test Case Validates Exception" +
                    " Response by passing Existing Email ID  " +
                    "Endpoint: " +POSTUsersEndpoint +  "  Input Request: " + jsonStr);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Test Case Validates Exception" +
                    " Response by passing Existing Email ID  "+
                    "Endpoint: " +POSTUsersEndpoint  + "  Input Request: " + jsonStr);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }

        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email has already been taken."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 2,description = "To Create User with  invalid Email")
    @Description ("Description : Testing Create User Endpoint the API whether Exception though with  HTTP Status 400")
    public void validateCreateUserEndPointWithInValidEmail() throws JSONException, IOException {
        ExtentTestManager.startTest("To Test if User Endpoint Thrown Proper Exceptions : " +
                "Sending Invalid Email Id ");
        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.InvalidUserEmail());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==400){
            ExtentTestManager.getTest().log(Status.INFO, "This Test Case validate if " +
                    "Valid email has sent" +
                     "Endpoint" + POSTUsersEndpoint + "  Input Request" + TestData.InvalidUserEmail());
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Test Case validate if " +
                    "Valid email has sent"+ "\n" +
                    "Endpoint:  " +POSTUsersEndpoint + "  Input Request: " + TestData.InvalidUserEmail());
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email must be a valid " +
                "email address.")," Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 3,description = "To Create User with  No Email")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithNoEmail() throws JSONException, IOException {

        ExtentTestManager.startTest("To Test if User Endpoint Thrown Proper Exceptions : " +
                "Sending request with no email id");
        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.emailEmpty());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==400){
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase Validate if exception " +
                    "has thrown when passing Empty Email Value  " +
                    "End Point  " + POSTUsersEndpoint + "  Inout Request" + TestData.emailEmpty());
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase Validate if exception " +
                    "has thrown when passing Empty Email Value  " +
                    "End Point  " + POSTUsersEndpoint + "  Inout Request" + TestData.emailEmpty());
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email field is required."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 4,description = "To Create User with  No Password")
    @Description ("Description : Testing Create User Endpoint - Whether proper exception thrown with proper " +
                                     "HTTP Status 400")
    public void validateCreateUserEndPointWithNoPassword() throws JSONException, IOException {
        ExtentTestManager.startTest("To Test if User Endpoint Thrown Proper Exceptions : " +
                "Sending request with no Password");
        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.passwordEmpty());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==400){
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase validate if exception has " +
                    "thrown when passing Empty Password Value  " +
                    "Endpoint: " +POSTUsersEndpoint + "  Input Request :" + TestData.passwordEmpty());
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase validate if exception has " +
                    "thrown when passing Empty Password Value  " +
                    "Endpoint: " +POSTUsersEndpoint + "  Input Request :" + TestData.passwordEmpty());
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
        assertTrue(restResponse.getResponse().getBody().asString().contains("The Password field is required."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 5,description = "To Create User with  Not a Valid Email")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithInValidEmailFormat() throws JSONException, IOException {
        ExtentTestManager.startTest("To Test if User Endpoint Thrown Proper Exceptions : " +
                "Sending request with invalid email format");
        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.inValidEmailFormat());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==400){
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase to validate " +
                    "user creation flow with  not a valid email" +
                    "Endpoint: " + POSTUsersEndpoint + "Input Request: " + TestData.inValidEmailFormat());
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase to validate " +
                    "user creation flow with  not a valid email" +
                    "Endpoint: " + POSTUsersEndpoint  + "Input Request: " + TestData.inValidEmailFormat());
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }

        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 6,description = "To Create User email with no domain")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithEmailNoDomain() throws JSONException, IOException {

        ExtentTestManager.startTest("To Test if User Endpoint Thrown Proper Exceptions : " +
                "Sending request with not a valid format - No domain address");
        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.inValidEmailNoDomain());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==400){
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase to validate " +
                    " user creation flow with not a valid email -  No Domain  " +
                    "InputRequest: " +POSTUsersEndpoint + "  Input Request: "
                    + TestData.inValidEmailNoDomain());
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase to validate " +
                    " user creation flow with not a valid email -  No Domain" +
                            "InputRequest: " +POSTUsersEndpoint + "Input Request: "
                            + TestData.inValidEmailNoDomain());
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  400");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email must be a valid email address."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @AfterSuite
    public void end() {
        ExtentTestManager.endTest();
    }

}
