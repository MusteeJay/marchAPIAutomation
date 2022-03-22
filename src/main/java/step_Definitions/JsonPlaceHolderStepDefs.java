package step_Definitions;

import com.jayway.jsonpath.DocumentContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.RequestBodyService;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonPlaceHolderStepDefs extends BaseSteps{
    Response responseForGetService, responseForGetComment, responseForPostComment, responseForUpdateComment, responseForCreateUserAccount;
    RequestBodyService requestBodyService;


    @Given("service is up and running")
    public void service_is_up_and_running() {
        setHeadersWithContentType();
        setEndpointPath(serviceUrl);
        getcall();
        responseForGetService = getResponse();
//        assertThat(responseForGetService.statusCode(), is(200));
        assertThat(responseForGetService.statusCode(), is(equalTo(200)));

    }
    @When("i search for {string} of a comment with a GET method")
    public void i_search_for_of_a_comment_with_a_get_method(String id) {
        setHeadersWithContentType();
        setEndpointPath(makeACommentEndpoint + id);
        getcall();
        responseForGetComment = getResponse();

    }
    @Then("i should get the correct {string}, {string}, {string} and {string} returned with status code of {int}")
    public void i_should_get_the_correct_and_returned_with_status_code_of(String id, String name, String email, String body, Integer stCode) {
        assertThat(responseForGetComment.statusCode(), is(stCode));
//        You have to convert the String declared in the feature file to Integer id for assertion using either of the 2 methods below
//        assertThat(responseForGetComment.body().jsonPath().get("id"), is(equalTo(Integer.parseInt(id))));
        assertThat(responseForGetComment.body().jsonPath().get("id"), is(equalTo(Integer.valueOf(id))));
        assertThat(responseForGetComment.body().jsonPath().get("name"), is(equalTo(name)));
        assertThat(responseForGetComment.body().jsonPath().get("email"), is(equalTo(email)));
        assertThat(responseForGetComment.body().jsonPath().get("body"), is(equalTo(body)));

    }
//    HTTP Post API Method Section
    @When("I create a new comment with the following details {string},{string}, {string} and {string},")
    public void iCreateANewCommentWithTheFollowingDetailsAnd(String postId, String name, String email, String body) {
        setHeadersWithContentType();
        setEndpointPath(makeACommentEndpoint);
//        write methods to enter or set the request body keys and values below (next 3 lines)
        requestBodyService = new RequestBodyService();
        DocumentContext requestBody = loadJsonTemplate(MakeACommentPayload);
        requestBodyService.SetRequestBodyForComment(requestBody, postId, name, email, body);

        getPostcall();
        responseForPostComment = getResponse();

    }

    @Then("i should get the correct {string},{string}, {string} and {string} returned and with status code of {int}")
    public void iShouldGetTheCorrectAndReturnedAndWithStatusCodeOf(String postId, String name, String email, String body, int stCode) {
        assertThat(responseForPostComment.statusCode(), is(stCode));
        assertThat(responseForPostComment.body().jsonPath().get("postId"), is(equalTo(postId)));
        assertThat(responseForPostComment.body().jsonPath().get("name"), is(equalTo(name)));
        assertThat(responseForPostComment.body().jsonPath().get("email"), is(equalTo(email)));
        assertThat(responseForPostComment.body().jsonPath().get("body"), is(equalTo(body)));
    }

    @When("I create a new comment with the following details {string},{string} and {string},")
    public void iCreateANewCommentWithTheFollowingDetailsAnd(String userId, String title, String body) {
        setHeadersWithContentType();
        setEndpointPath(makeAPostEndpoint);
//        write methods to enter or set the request body keys and values below (next 3 lines)
        requestBodyService = new RequestBodyService();
        DocumentContext requestBody = loadJsonTemplate(MakeAPostPayload);
        requestBodyService.SetRequestBodyForPost(requestBody, userId, title, body);

        getPostcall();
        responseForPostComment = getResponse();
    }

    @Then("i should get the correct response {string},{string}, {string} and {string} returned and with status code of {int}")
    public void iShouldGetTheCorrectResponseAndReturnedAndWithStatusCodeOf(String userid, String title, String body, String id, int stCode) {
        assertThat(responseForPostComment.statusCode(), is(stCode));
        assertThat(responseForPostComment.body().jsonPath().get("userId"), is(userid));
        assertThat(responseForPostComment.body().jsonPath().get("title"), is(equalTo(title)));
        assertThat(responseForPostComment.body().jsonPath().get("body"), is(equalTo(body)));
        assertThat(responseForPostComment.body().jsonPath().get("id"), is(Integer.valueOf(id)));
    }

    @When("I create an update to a new post with the following details {string},{string} and {string},")
    public void iCreateAnUpdateToANewPostWithTheFollowingDetailsAnd(String userId, String title, String body) {
        setHeadersWithContentType();
        int id = 3;
        setEndpointPath(makeAPostEndpoint + id);
        requestBodyService = new RequestBodyService();
        DocumentContext requestBody = loadJsonTemplate(UpdateAPostPayload);
        requestBodyService.SetRequestBodyForUpdate(requestBody, userId, title, body);
        getUpdatePostcall();
        responseForUpdateComment = getResponse();
    }

    @Then("i should get the correct response {string},{string}, {string} and {string} returned with status code of {int}")
    public void iShouldGetTheCorrectResponseAndReturnedWithStatusCodeOf(String userId, String title, String body, String id, int stCode) {
        assertThat(responseForUpdateComment.statusCode(), is(stCode));
        assertThat(responseForUpdateComment.body().jsonPath().get("userId"), is(userId));
        assertThat(responseForUpdateComment.body().jsonPath().get("title"), is(equalTo(title)));
        assertThat(responseForUpdateComment.body().jsonPath().get("body"), is(equalTo(body)));
        assertThat(responseForUpdateComment.body().jsonPath().get("id"), is(Integer.valueOf(id)));
    }

    @When("I create a new user with the following details {string},{string}, {string}, {string}, {string} and {string},")
    public void iCreateANewUserWithTheFollowingDetailsAnd(String name, String username, String street, String suite, String phone, String catchPhrase) {
        setHeadersWithContentType();
        setEndpointPath(makeACommentEndpoint);
        requestBodyService = new RequestBodyService();
        DocumentContext requestBody = loadJsonTemplate(CreateUserAccountPayload);
        requestBodyService.SetRequestBodyForUser(requestBody, name, username, street, suite, phone, catchPhrase);
        getPostcall();
        responseForCreateUserAccount = getResponse();
        
    }

    @Then("i should get the correct response {string},{string}, {string}, {string}, {string} and {string} returned with status code of {int}")
    public void iShouldGetTheCorrectResponseAndReturnedWithStatusCodeOf(String name, String username, String street, String suite, String phone, String catchP, int stCod) {
        assertThat(responseForCreateUserAccount.statusCode(), is(stCod));
        assertThat(responseForCreateUserAccount.body().jsonPath().get("name"), is(equalTo(name)));
        assertThat(responseForCreateUserAccount.body().jsonPath().get("username"), is(equalTo(username)));
        assertThat(responseForCreateUserAccount.body().jsonPath().get("address.street"), is(equalTo(street)));
        assertThat(responseForCreateUserAccount.body().jsonPath().get("address.suite"), is(equalTo(suite)));
        assertThat(responseForCreateUserAccount.body().jsonPath().get("phone"), is(equalTo(phone)));
        assertThat(responseForCreateUserAccount.body().jsonPath().get("company.catchPhrase"), is(equalTo(catchP)));
    }
}
