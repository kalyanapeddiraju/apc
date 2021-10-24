package testapi;

import libapihelper.ExtentReportBuilder;
import libapihelper.Helpers;
import libapihelper.RestResponse;
import libapihelper.RequestBuilder;
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
import static libapihelper.ExtentReportBuilder.intilizeExtentreport;
import static libapihelper.ExtentReportBuilder.logExtentReport;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestForSampleAPIGET {
    RequestBuilder requestBuilder;
    RestResponse restResponse;
    String GETusersEndpoint, GETPostsEndpoint,GETCommentsEndpoint,endpointURL;
    Properties properties;
    //String RESOURCEPATHEXCEPTEDRESPONSE ="src/test/java/resources/ExceptedResponse/";
    @BeforeSuite
    public void suiteSetup() throws IOException {
        properties = Helpers.loadProperties();
        endpointURL = properties.getProperty("endpointURL");
        intilizeExtentreport("GET USER / COMMENTS / User API",
                "  -- This Test Verifies the User Comments \" +\n" +
                "                \"and Posts Endpoint","GET API" );
    }

    // GET All users
    @Test(priority = 1, description = "To get the details of a user's Posts and comments")
    @Description ("Description : Testing the API whether success  200")
    public void validateJSONPlaceHolderWorkFlow() throws JSONException, IOException {
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
        actualjsonResponseGetUser = new JSONObject(actualResponse);
        Helpers.jsonXAssertEquals("Validate Get User Response", actualjsonResponseGetUser.toString(),
                exceptedResponseGetUsers, JSONCompareMode.STRICT);
        String id = restResponse.getResponse().path("find{it.username=='Delphine'}.id").toString();

        logExtentReport(GETusersEndpoint, restResponse.getResponse().
                        getBody().asString(),"",
                String.valueOf(restResponse.getResponse().getStatusCode()),"200" ,
                "This Testcase to Get Specific User");

        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> Get Users");

        //Get Posts for a Particular User ID
        GETPostsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getURIAllPosts")+ id;
        String exceptedResponseGetPosts = Helpers.getJsonFromResource(RESOURCEPATHEXCEPTEDRESPONSE+
                "getPostsById.json");

        requestBuilder = new RequestBuilder("GET", GETPostsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        Helpers.jsonXAssertEquals("Validate Posts by user Response", restResponse.getResponse().asString(),
                exceptedResponseGetPosts, JSONCompareMode.STRICT);

        logExtentReport(GETPostsEndpoint, restResponse.getResponse().
                        getBody().asString(),"",
                String.valueOf(restResponse.getResponse().getStatusCode()),"200" ,
                "This Testcase to get the Post's for a particular User");

        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> GET Posts by ID");

        //Get Comments for the specific User
        GETCommentsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getURIAllComments") + id;
        String exceptedResponseGetComments = Helpers.getJsonFromResource(RESOURCEPATHEXCEPTEDRESPONSE +
                "getCommentsById.json");

        requestBuilder = new RequestBuilder("GET", GETCommentsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        assertTrue(Helpers.isValidEmailAddress(restResponse.getResponse().<String>path("email")),
                "Email is invalid");

        logExtentReport(GETCommentsEndpoint, restResponse.getResponse().asString(),exceptedResponseGetComments,
                String.valueOf(restResponse.getResponse().getStatusCode()),"200" ,
                "This Testcase to validate if email format is correct in the Get Comments response ");

        Helpers.jsonXAssertEquals("Validate Comments by User Response", restResponse.getResponse().asString(),
                exceptedResponseGetComments, JSONCompareMode.STRICT);
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> GET Comments by ID");
    }

    @Test (priority = 2,description = "To get Multiple posts")
    @Description ("Description : Testing the  Posts Endpoint whether return multiple results")
    public void validateForMultiplePostsPerUserComments() {
        //Get multiple Posts
        GETPostsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getUserComments");
        requestBuilder = new RequestBuilder("GET", GETPostsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        assertTrue(restResponse.getResponse().jsonPath().getList("$").size() > 1,
                " Issue in Fetching Multiple Records");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200",
                "MisMatch HTTPS Status code >> GET Posts by ID");
    }
    @Test (priority = 3,description = "To Check Exception Cases")
    @Description ("Description : Testing the Post Endpoint whether return proper exception HTTP Status")
    public void validateUserPostsCommentsExceptionScenarioForNonExistUserId() {
        //Get multiple Posts
        GETPostsEndpoint = System.getProperty("endpointURLGET") + System.getProperty("getURIPostByComments");
        requestBuilder = new RequestBuilder("GET", GETPostsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(GETPostsEndpoint, restResponse.getResponse().asString(),"",
                String.valueOf(restResponse.getResponse().getStatusCode()),"404" ,
                "This Testcase to check the proper exception thrown when passing non exist userid");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "404",
                "MisMatch HTTPS Status code >> GET Posts/1456/comments");

    }

    @Test (priority = 3,description = "To Check the Comments Endpoint by passing query parameter ")
    @Description ("Description : Testing the Comments Endpoint whether return multiple results")
    public void validateForMultipleCommentsWithQueryParameter() {
        //Get multiple Posts
        GETCommentsEndpoint = System.getProperty("endpointURLGET") +
                System.getProperty("getURICommentsWithQueryParameter");
        requestBuilder = new RequestBuilder("GET", GETCommentsEndpoint, "");
        restResponse = RestResponse.getRestResponse(requestBuilder);

        logExtentReport(GETCommentsEndpoint, restResponse.getResponse().asString(),"",
                String.valueOf(restResponse.getResponse().getStatusCode()),"200" ,
                "This Testcase fetch the GET comments by passing Query parameter values");

        assertTrue(restResponse.getResponse().jsonPath().getList("$").size() > 1,
                "Issue in Fetching Records");
        assertEquals(String.valueOf(restResponse.getStatusCode()), "200", "MisMatch HTTPS Status " +
                "code >> GET /Comments?postId=1");
    }
    @AfterSuite
    public void end() {
        ExtentReportBuilder.onFinish();
    }
}
