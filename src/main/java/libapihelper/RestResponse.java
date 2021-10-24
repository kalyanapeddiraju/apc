package libapihelper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;


public class RestResponse {

    Response response;
    Headers headers;
    int statusCode;
    long responseTime;
    private static RequestBuilder requestBuilder;
    private static RequestSpecification requestSpec;

    public static RestResponse getRestResponse(RequestBuilder RequestBuilder) {
        requestBuilder = RequestBuilder;
        requestSpec = buildDefaultRequestSpec();
        return getRestAssuredResponse();
    }

    private static RequestSpecification buildDefaultRequestSpec() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        Headers requestHeaders = requestBuilder.getHeaders();

        if(requestHeaders !=null) {
            for (Header header : requestHeaders) {
                builder.addHeader(header.getName(), header.getValue());
            }
        }
        if(requestBuilder.getAccept()!=null) {
            builder.addHeader("accept", requestBuilder.getAccept());
        }

        if(requestBuilder.getContentType()!=null) {
            builder.addHeader("Content-Type", requestBuilder.getContentType());
        }

        if(!requestBuilder.getUrl().contains("https://")) {
            builder.setPort(80);
        }

        else {
            builder.setPort(443);
        }

        return builder.build();
    }

    private static RestResponse getRestAssuredResponse() {
        RestResponse RestResponse = new RestResponse();
        Response restResponseObject = null;
        List<String> updateMethods = Arrays.asList( "post", "delete", "put", "patch") ;

        if(updateMethods.contains(requestBuilder.getMethod().toLowerCase())) {
            restResponseObject = performUpdateRequest();
        }
        else if(requestBuilder.getMethod().equalsIgnoreCase("get")) {
            restResponseObject =
                    given()
                            .headers("Authorization","Bearer 50e6d74d26fd5d6debd8e25a313bfa79f59943647637f239a02f0ff12cb641fd")
                            .spec(requestSpec)
                            .when()
                            .get(requestBuilder.getUrl())
                            .then()
                            .extract()
                            .response();
        }

        RestResponse.response = restResponseObject;
        RestResponse.headers = restResponseObject.headers();
        RestResponse.statusCode = restResponseObject.getStatusCode();
        RestResponse.responseTime = restResponseObject.getTime();

        return RestResponse;
    }

    private static Response performUpdateRequest(){
        Response restResponseObject;
        if (requestBuilder.getBody() != null) {
            restResponseObject =
                    given()
                            .headers("Authorization","Bearer 50e6d74d26fd5d6debd8e25a313bfa79f59943647637f239a02f0ff12cb641fd")
                            .spec(requestSpec)
                            .body(requestBuilder.getBody())
                            .when()
                            .request(requestBuilder.getMethod(), requestBuilder.getUrl())
                            .then()
                            .extract()
                            .response();
        } else {
            restResponseObject =
                    given()
                            .spec(requestSpec)
                            .when()
                            .request(requestBuilder.getMethod(), requestBuilder.getUrl())
                            .then()
                            .extract()
                            .response();
        }

        return restResponseObject;
    }

    public Response getResponse() {    return response;  }
    public Headers getHeaders() {
        return headers;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public long getResponseTime() {
        return responseTime;
    }
}
