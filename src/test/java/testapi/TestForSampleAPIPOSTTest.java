package testapi;


import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Description;
import libapihelper.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.*;
import resources.InputRequest.TestData;

import java.io.IOException;
import java.util.Properties;

//import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static libapihelper.ExtentReportBuilder.intilizeExtentreport;
import static libapihelper.ExtentReportBuilder.logExtentReport;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestForSampleAPIPOSTTest {
    RequestBuilder requestBuilder;
    RestResponse restResponse;
    String POSTUsersEndpoint, GETUserEndpoint,endpointURL,resourcePath,requestBody,exceptedEmail,jsonStr;
    Properties properties;

    @BeforeSuite
    public void suiteSetup() throws IOException {
        properties = Helpers.loadProperties();
        POSTUsersEndpoint=System.getProperty("endpointURLPOST")+ System.getProperty("userURIPOST");

//        intilizeExtentreport(" Create USER API  ",
//                "  -- This Test Verifies the User Creation Flow", "USERAPI");
    }

  //  @Test (priority = 1,description = "To Create User with  Not a Valid Email")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithInValidEmailFormat() throws JSONException, IOException {

       ExtentTestManager.startTest("This Testcase to validate to " +
                "create user with not a valid email");
        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.inValidEmailFormat());
        restResponse = RestResponse.getRestResponse(requestBuilder);

        ExtentTestManager.getTest().log(Status.INFO, "Actual HTTP Status is : "+ restResponse.getStatusCode());
        ExtentTestManager.getTest().log(Status.INFO, "Excepted HTTP Status is :  400");
        ExtentTestManager.getTest().log(Status.INFO, "Actual Response is :  "+ restResponse.getResponse().asString());

        assertEquals(String.valueOf(restResponse.getStatusCode()), "400", "MisMatch HTTPS Status " +
                "code >> Create User");
    }

    @Test (priority = 1,description = "To Create User email with no domain")
    @Description ("Description : Testing Create User Endpoint , whether Exception though with HTTP Status 400")
    public void validateCreateUserEndPointWithEmailNoDomain() throws JSONException, IOException {

       ExtentTestManager.startTest("This Testcase to validate to " +
                "create user with not a valid email - No Domain");
        requestBuilder = new RequestBuilder ("POST", POSTUsersEndpoint, TestData.inValidEmailNoDomain());
        restResponse = RestResponse.getRestResponse(requestBuilder);
        ExtentTestManager.getTest().log(Status.INFO, "Actual HTTP Status is : "+ restResponse.getStatusCode());
        ExtentTestManager.getTest().log(Status.INFO, "Excepted HTTP Status is :  400");
        if(restResponse.getStatusCode()==400){
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+ restResponse.getResponse().asString());
        }else{
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  "+ restResponse.getResponse().asString());
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
