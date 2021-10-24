package testapi;

import com.aventstack.extentreports.Status;
import libapihelper.*;
import io.qameta.allure.Description;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static constants.TestConstants.RESOURCEPATHEXCEPTEDRESPONSE;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestForSampleAPIGET {
    RequestBuilder requestBuilder;
    RestResponse restResponse;
    String GETusersEndpoint, GETPostsEndpoint,GETCommentsEndpoint,endpointURL;
    Properties properties;
    @BeforeSuite
    public void suiteSetup() throws IOException {
        properties = Helpers.loadProperties();
        endpointURL = properties.getProperty("endpointURL");
    }

    // GET All users
    @Test(priority = 1, description = "To get the details of a user's Posts and comments")
    @Description ("Description : Testing the API whether success  200")
    public void validateJSONPlaceHolderWorkFlow() throws JSONException, IOException {

        ExtentTestManager.startTest("To Test for Get Specific User and Find Comments and " +
                "Posts for identified User");
        //End point Setup
        GETusersEndpoint=System.getProperty("endpointURLGET") + System.getProperty("getURIUsers");

        // Rest Builder
        requestBuilder = new RequestBuilder("GET", GETusersEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        String exceptedResponseGetUsers = Helpers.getJsonFromResource(RESOURCEPATHEXCEPTEDRESPONSE +
                "getUser.json");
        // To find Specific User from GET Response

        HashMap<String, String> actualResponse = restResponse.getResponse().path("find{it.username=='Delphine'}");
        JSONObject actualjsonResponseGetUser;

        if(restResponse.getStatusCode()==200){
            ExtentTestManager.getTest().log(Status.INFO, "Validate GET USER Endpoint "+GETusersEndpoint);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted Response is :  "+
                    exceptedResponseGetUsers);
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "Validate GET USER Endpoint " + GETusersEndpoint);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted Response is :  "+
                    exceptedResponseGetUsers);
        }
        actualjsonResponseGetUser = new JSONObject(actualResponse);
        Helpers.jsonXAssertEquals("Validate Get User Response", actualjsonResponseGetUser.toString(),
                exceptedResponseGetUsers, JSONCompareMode.STRICT);
        String id = restResponse.getResponse().path("find{it.username=='Delphine'}.id").toString();

        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> Get Users");


        //Get Posts for a Particular User ID
        GETPostsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getURIAllPosts")+ id;
        String exceptedResponseGetPosts = Helpers.getJsonFromResource(RESOURCEPATHEXCEPTEDRESPONSE+
                "getPostsById.json");
        requestBuilder = new RequestBuilder("GET", GETPostsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==200){
            ExtentTestManager.getTest().log(Status.INFO, "Validate GET USER POSTS "+ GETPostsEndpoint);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+ restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted Response is :  "+
                    exceptedResponseGetPosts);
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "Validate GET USER POSTS" + GETPostsEndpoint);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted Response is :  "+
                    exceptedResponseGetPosts);
        }
        Helpers.jsonXAssertEquals("Validate Posts by user Response", restResponse.getResponse().asString(),
                exceptedResponseGetPosts, JSONCompareMode.STRICT);
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> GET Posts by ID");

        //Get Comments for the specific User
        GETCommentsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getURIAllComments") + id;
        String exceptedResponseGetComments = Helpers.getJsonFromResource(RESOURCEPATHEXCEPTEDRESPONSE +
                "getCommentsById.json");

        requestBuilder = new RequestBuilder("GET", GETCommentsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        ExtentTestManager.getTest().log(Status.INFO, "Validate if email " +
                "format is correct in the Get Comments response from the Response" +
                "  "+ restResponse.getResponse().<String>path("email"));
        assertTrue(Helpers.isValidEmailAddress(restResponse.getResponse().<String>path("email")),
                "Email is invalid");

        if(restResponse.getStatusCode()==200){
            ExtentTestManager.getTest().log(Status.INFO, "Validate GET USER Comments " +
                    "By ID Endpoint " +GETCommentsEndpoint);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted Response is :  "+
                    exceptedResponseGetUsers);
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "Validate GET USER Comments " +
                    "By ID Endpoint " + GETCommentsEndpoint);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted Response is :  " +
                    exceptedResponseGetUsers);
        }
        Helpers.jsonXAssertEquals("Validate Comments by User Response", restResponse.getResponse().asString(),
                exceptedResponseGetComments, JSONCompareMode.STRICT);
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> GET Comments by ID");
    }

    @Test (priority = 2,description = "To get Multiple posts")
    @Description ("Description : Testing the  Posts Endpoint whether return multiple results")
    public void validateForMultiplePostsPerUserComments() {
        ExtentTestManager.startTest("To Test Post Endpoint if return multiple results");
        //Get multiple Posts
        GETPostsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getUserComments");
        requestBuilder = new RequestBuilder("GET", GETPostsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);
        if(restResponse.getStatusCode()==200){
            ExtentTestManager.getTest().log(Status.INFO, " To Check - GET  Multiple Records " +
                    "By ID Endpoint " +GETPostsEndpoint);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "To Check GET Multiple Records " +
                    "By ID Endpoint " + GETPostsEndpoint);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
        assertTrue(restResponse.getResponse().jsonPath().getList("$").size() > 1,
                " Issue in Fetching Multiple Records");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200",
                "MisMatch HTTPS Status code >> GET Posts by ID");
    }
    @Test (priority = 3,description = "To Check Exception Cases")
    @Description ("Description : To Test Post Endpoint if it return proper exception and HTTP Status")
    public void validateUserPostsCommentsExceptionScenarioForNonExistUserId() {
        ExtentTestManager.startTest("To Test Post Endpoint if return proper exception HTTP Status");
        //Get multiple Posts
        GETPostsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getURIPostByComments");
        requestBuilder = new RequestBuilder("GET", GETPostsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);
        if(restResponse.getStatusCode()==404){
            ExtentTestManager.getTest().log(Status.INFO, " This Testcase to check the proper " +
                    "exception thrown when passing non exist userid " +
                    "By ID Endpoint " +GETPostsEndpoint);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  404");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase to check the proper " +
                    "exception thrown when passing non exist userid " +
                    "By ID Endpoint " + GETPostsEndpoint);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
    }

    @Test (priority = 3,description = "To Check the Comments Endpoint by passing query parameter ")
    @Description ("Description : Testing the Comments Endpoint whether return multiple results")
    public void validateForMultipleCommentsWithQueryParameter() {
        ExtentTestManager.startTest("To test  GET Comments Endpoint if it returns " +
                "results while using Query parameters");
        //Get multiple Posts
        GETCommentsEndpoint = System.getProperty("endpointURLGET") +
                System.getProperty("getURICommentsWithQueryParameter");
        requestBuilder = new RequestBuilder("GET", GETCommentsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        if(restResponse.getStatusCode()==404){
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase fetch the GET comments" +
                    " by passing Query parameter values  " +GETCommentsEndpoint);
            ExtentTestManager.getTest().log(Status.PASS, "Actual HTTP Status is : "+
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.PASS, "Excepted HTTP Status is :  404");
            ExtentTestManager.getTest().log(Status.PASS, "Actual Response is :  "+
                    restResponse.getResponse().asString());
        }else {
            ExtentTestManager.getTest().log(Status.INFO, "This Testcase fetch the GET comments" +
                    " by passing Query parameter values" + GETCommentsEndpoint);
            ExtentTestManager.getTest().log(Status.FAIL, "Actual HTTP Status is : " +
                    restResponse.getStatusCode());
            ExtentTestManager.getTest().log(Status.FAIL, "Excepted HTTP Status is :  200");
            ExtentTestManager.getTest().log(Status.FAIL, "Actual Response is :  " +
                    restResponse.getResponse().asString());
        }
        assertTrue(restResponse.getResponse().jsonPath().getList("$").size() > 1,
                "Issue in Fetching Records");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> GET /Comments?postId=1");
    }
    @AfterSuite
    public void end() {
        ExtentTestManager.endTest();
    }
}
