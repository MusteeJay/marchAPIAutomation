package step_Definitions;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseSteps {
    public String serviceUrl;
    public String restfulServiceUrl;
    //    postman request body is called payload
    public String MakeAPostPayload, MakeACommentPayload, UpdateAPostPayload, CreateUserAccountPayload,
            RestfulBookNewUserPayload, RestfulGetUserIDPayload, RestfulBookerUpdatePayload,
            RestfulBookerCreateTokenPayload, RestfulUpdateAUserPayload;
    public String makeAPostEndpoint, makeACommentEndpoint, createAUserEndpoint, restfulMakeAPostEndpoint,
            restfulGetEndpoint, restfulCreatTokenEndpoint;
    public Headers headers;
    private String endpointPath;
    private Response response;
    public DocumentContext requestBodyJson;


    public BaseSteps() {
        MakeACommentPayload = "/templates/JsonPlaceholder/CommentOnPost.json";
        MakeAPostPayload = "/templates/JsonPlaceholder/MakeAPost.json";
        UpdateAPostPayload = "/templates/JsonPlaceholder/UpdateAPost.json";
        CreateUserAccountPayload = "/templates/JsonPlaceholder/CreateUsers.json";
        RestfulBookNewUserPayload = "/templates/Restful-Booker/restfulMakeAPost.json";
        RestfulGetUserIDPayload = "/templates/Restful-Booker/restfulGetUserID.json";
        RestfulBookerCreateTokenPayload = "/templates/Restful-Booker/RestfulCreateAToken.json";
        RestfulBookerUpdatePayload = "/templates/Restful-Booker/restfulUpdateAnAccount.json";
        RestfulUpdateAUserPayload = "/templates/Restful-Booker/restUpdateByPut.json";
        serviceUrl = "https://jsonplaceholder.typicode.com/";
        restfulServiceUrl = "https://restful-booker.herokuapp.com/";
        restfulMakeAPostEndpoint = restfulServiceUrl + "booking/";
        restfulGetEndpoint = restfulServiceUrl + "ping/";
        restfulCreatTokenEndpoint = restfulServiceUrl + "auth/";
        makeACommentEndpoint = serviceUrl + "comments/";
        makeAPostEndpoint = serviceUrl + "posts/";
//        createAUserEndpoint = serviceUrl + "users/";
    }

    public void setHeaders(Headers value) {
//        resetHeaders();
        headers = null;
        headers = value;
    }
//    private void resetHeaders(){}

    public void setHeadersWithContentType() {
        headers = new Headers(
                new Header("Content-Type", "application/json"));
        new Header("Accept", "application/json");
        setHeaders(headers);
    }

    public void setHeadersWithManyHeaders() {
        headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("accept", "application/json"),
                new Header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM="));
                  setHeaders(headers);
    }
    public void setHeadersWithManyHeadersAndToken() {
        headers = new Headers(
                new Header("Content-Type", "application/json"),
                new Header("accept", "application/json"),
                new Header("Token", "da252cab351c733"));
        setHeaders(headers);
    }

    protected URL getURL() {
        try {
            return new URL(endpointPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }
    }

    public Response getcall() {
        response = RestAssured.given()
                .log().all()
                .relaxedHTTPSValidation()
                .headers(headers)
                .when()
                .get(getURL())
                .then()
                .log().all()
                .extract()
                .response();
        return response;
    }

    public Response getPostcall() {
        response = RestAssured.given()
                .log().all()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(requestBodyJson.jsonString())
                .when()
                .post(getURL())
                .then()
                .log().all()
                .extract()
                .response();
        return response;
    }

    public Response getUpdatePostcall() {
        response = RestAssured.given()
                .log().all()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(requestBodyJson.jsonString())
                .when()
                .put(getURL())
                .then()
                .log().all()
                .extract()
                .response();
        return response;
    }
    public Response getAPatchcall () {
        response = RestAssured.given()
                .log().all()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(requestBodyJson.jsonString())
                .when()
                .patch(getURL())
                .then()
                .log().all()
                .extract()
                .response();
        return response;
    }
    public void setEndpointPath(String endpointPath) {
        this.endpointPath = endpointPath;
    }

    public Response getResponse() {
        return response;
    }
//    public void setRequestBody(DocumentContext requestBody){this.requestBodyJson = requestBody;}

    public DocumentContext loadJsonTemplate(String path) {
        requestBodyJson = JsonPath.parse(this.getClass().getResourceAsStream(path));
        return requestBodyJson;
    }


}
