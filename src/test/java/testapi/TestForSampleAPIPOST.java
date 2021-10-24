package testapi;


import libapihelper.ExtentReportBuilder;
import libapihelper.Helpers;
import libapihelper.RestResponse;
import libapihelper.RequestBuilder;
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

import static libapihelper.ExtentReportBuilder.intilizeExtentreport;
import static libapihelper.ExtentReportBuilder.logExtentReport;
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
        intilizeExtentreport(" Create USER API  ",
                "  -- This Test Verifies the User Creation Flow", "USERAPI");
    }

    @Test (priority = 1,description = "To Create User with Valid Email")
    @Description ("Description : Testing Create User Endpoint the API whether success 200 and Exception 400")
    public void validateCreateUserEndpointWithValidExitingEmail() throws JSONException, IOException {

        jsonStr = TestData.ValidUserEmail();
        JSONObject jsonObj = new JSONObject(jsonStr);
        exceptedEmail = jsonObj.getString("email");

       // POSTUsersEndpoint=System.getProperty("endpointURL")+System.getProperty("userURI");

        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, jsonStr);
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(POSTUsersEndpoint, restResponse.getResponse().asString(),exceptedEmail,
                String.valueOf(restResponse.getResponse().getStatusCode()),"200" ,
                "This Test Case Validate Success response for Crate User");

        assertEquals(restResponse.getResponse().path("result.email"),exceptedEmail,"Email Id Mismatch ");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> Create User");
        // To check with Exiting Email ID
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(POSTUsersEndpoint, restResponse.getResponse().asString(),
                "The Email has already been taken.",
                String.valueOf(restResponse.getResponse().getStatusCode()),"400" ,
                "This Test Case Validates Exception Response by passing Existing Email ID");

        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email has already been taken."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 2,description = "To Create User with  invalid Email")
    @Description ("Description : Testing Create User Endpoint the API whether Exception though with  HTTP Status 400")
    public void validateCreateUserEndPointWithInValidEmail() throws JSONException, IOException {

        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.InvalidUserEmail());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(POSTUsersEndpoint, restResponse.getResponse().asString(),
                "The Email must be a valid ",
                String.valueOf(restResponse.getResponse().getStatusCode()),"400",
                " This Test Case validate if Valid email has sent");

        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email must be a valid " +
                "email address.")," Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 3,description = "To Create User with  No Email")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithNoEmail() throws JSONException, IOException {

        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.emailEmpty());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(POSTUsersEndpoint, restResponse.getResponse().asString(),
                "The Email field is required.",
                String.valueOf(restResponse.getResponse().getStatusCode()),"400" ,
                "This Testcase Validate if exception has thrown when passing Empty Email Value");

        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email field is required."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 4,description = "To Create User with  No Password")
    @Description ("Description : Testing Create User Endpoint - Whether proper exception thrown with proper " +
                                     "HTTP Status 400")
    public void validateCreateUserEndPointWithNoPassword() throws JSONException, IOException {

        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.passwordEmpty());
        restResponse = RestResponse.getRestResponse(requestBuilder);
        logExtentReport(POSTUsersEndpoint, restResponse.getResponse().asString(),
                "The Password field is required.",
                String.valueOf(restResponse.getResponse().getStatusCode()),"400",
                "This Testcase validate if exception has thrown when passing Empty Password Value" );

        assertTrue(restResponse.getResponse().getBody().asString().contains("The Password field is required."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 5,description = "To Create User with  Not a Valid Email")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithInValidEmailFormat() throws JSONException, IOException {

        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.inValidEmailFormat());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(POSTUsersEndpoint, restResponse.getResponse().asString(),
                "", String.valueOf(restResponse.getResponse().getStatusCode()),
                "400" ,"This Testcase to validate to " +
                        "create user with not a valid email");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 6,description = "To Create User email with no domain")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithEmailNoDomain() throws JSONException, IOException {

        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.inValidEmailNoDomain());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(POSTUsersEndpoint, restResponse.getResponse().asString(),
                "The Email must be a valid email address.",
                String.valueOf(restResponse.getResponse().getStatusCode()),"400" ,
                "This Testcase to validate to" +
                        "create user with not a valid email -  No Domain");

        assertTrue(restResponse.getResponse().getBody().asString().contains("The Email must be a valid email address."),
                " Mismatch Json Response");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @AfterSuite
    public void end() {
        ExtentReportBuilder.onFinish();
    }

}
