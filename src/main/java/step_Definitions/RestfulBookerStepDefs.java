package step_Definitions;

import com.jayway.jsonpath.DocumentContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.RestfulRequestBodyService;

import java.io.StringReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class RestfulBookerStepDefs extends BaseSteps {
    RestfulRequestBodyService restfulRequestBodyService;
    Response responseForGetService, responseForPostService, responseForPatchService, responseForGetMethod, responseForCreateToken;
    String generatedId, bookingid, generatedToken;


    @Given("Restful-booker service is up and running")
    public void restful_booker_service_is_up_and_running() {
        setHeadersWithContentType();
        setEndpointPath(restfulGetEndpoint);
        getcall();
        responseForGetService = getResponse();
        assertThat(responseForGetService.statusCode(), is(201));


    }

    @When("I book a new user with the following details {string},{string}, {string}, {string}, {string} and {string},")
    public void i_book_a_new_user_with_the_following_details_and(String firstname, String lastname, String totalrice,
                                                                 String depositpaid, String checkin, String checkout) {
        setHeadersWithContentType();
        setEndpointPath(restfulMakeAPostEndpoint);
        restfulRequestBodyService = new RestfulRequestBodyService();
        DocumentContext requestBody = loadJsonTemplate(RestfulBookNewUserPayload);
        restfulRequestBodyService.SetRestfulRequestBodyForPost(requestBody, firstname, lastname, totalrice, depositpaid,
                checkin, checkout);
        getPostcall();
        responseForPostService = getResponse();

    }

    @Then("i should get the new user details with {string}, {string}, {string}, {string},{string} and {string} returned with status code of {int}")
    public void iShouldGetTheNewUserDetailsWithAndReturnedWithStatusCodeOf(String bookId, String fName,
                                                                           String lName, String totPrice, String depPaid, String cheIn, Integer stCode) {
//      generatedId = responseForPostService.body().jsonPath().get("bookingid");
////            bookingid = bookId;
//            bookingid = bookId;
//            System.out.println("The new user generated ID IS : " + bookId);
        assertThat(responseForPostService.statusCode(), is(stCode));
        assertThat(responseForPostService.body().jsonPath().get("bookingid"), is(notNullValue()));
//        assertThat(responseForPostService.body().jsonPath().get("bookingid"), is(equalTo(Integer.valueOf(bookId))));
        responseForPostService.body().jsonPath().get(bookId);
        assertThat(responseForPostService.body().jsonPath().get("booking.firstname"), is(equalTo(fName)));
        assertThat(responseForPostService.body().jsonPath().get("booking.lastname"), is(equalTo(lName)));
        assertThat(responseForPostService.body().jsonPath().get("booking.totalprice"), is(equalTo(Integer.valueOf(totPrice))));
//        assertThat(responseForPostService.body().jsonPath().get("booking.depositpaid"), is(true));
        assertThat(responseForPostService.body().jsonPath().get("booking.depositpaid"), is(Boolean.valueOf(true)));
        assertThat(responseForPostService.body().jsonPath().get("booking.bookingdates.checkin"), is(equalTo(cheIn)));
    }

    @And("I should be able to assess the generated {string} of the new booked user.")
    public void iShouldBeAbleToAssessTheGeneratedOfTheNewBookedUser(String ID) {
        restfulRequestBodyService = new RestfulRequestBodyService();
        DocumentContext responseBodyForUser = loadJsonTemplate(RestfulGetUserIDPayload);
        restfulRequestBodyService.SetRestfulResponseBodyGetUserID(responseBodyForUser, bookingid);
        responseForPostService = getResponse();
        responseForPostService.body().jsonPath().getInt("bookingid");
//        generatedId = responseForPostService.body().jsonPath().getInt("bookingid");
        System.out.println("the new user ID is:" + " " + responseForPostService.body().jsonPath().getInt("bookingid"));

    }

    //        Generate Token Section
    @When("I book a new user with the following details {string} and {string},")
    public void iBookANewUserWithTheFollowingDetailsAnd(String username, String password) {
        setHeadersWithContentType();
        setEndpointPath(restfulCreatTokenEndpoint);
        restfulRequestBodyService = new RestfulRequestBodyService();
        DocumentContext requestBody = loadJsonTemplate(RestfulBookerCreateTokenPayload);
        restfulRequestBodyService.setRestfulRequestBodyCreateToken(requestBody, username, password);
        getPostcall();
        responseForCreateToken = getResponse();
        generatedToken = responseForCreateToken.body().jsonPath().get("token");
        System.out.println("The generated token is :" + generatedToken);
    }
    @Then("i should get the new {string} generated returned with status code of {int}")
    public void iShouldGetTheNewGeneratedReturnedWithStatusCodeOf(String tok, int stCode) {
        assertThat(responseForCreateToken.statusCode(), is(stCode));
        assertThat(responseForCreateToken.body().jsonPath().get("token"), is(notNullValue()));
    }


//                           HTTP PATCH SECTION

    @When("I partially update a booked user account with the following details {string}, {string}, {string}, {string} and {string}")
    public void iPartiallyUpdateABookedUserAccountWithTheFollowingDetailsAnd(String totalPrice, String depositpaid,
                                                                             String checkin, String checkout, String additionalneeds) {
        setHeadersWithManyHeaders();
        int ID = 20;
        setEndpointPath(restfulMakeAPostEndpoint + ID);
        restfulRequestBodyService = new RestfulRequestBodyService();
        DocumentContext responseBody = loadJsonTemplate(RestfulBookerUpdatePayload);
        restfulRequestBodyService.SetRestfulRequestBodyForUpdate(responseBody, totalPrice, depositpaid, checkin, checkout, additionalneeds);
        getAPatchcall();
        responseForPatchService = getResponse();
    }


    @Then("i should get the new user details with {string},{string}, {string}, {string} and {string} returned with status code of {int}")
    public void iShouldGetTheNewUserDetailsWithAndReturnedWithStatusCodeOf(String totPr, String dPaid, String checkin, String cOut,
                                                                           String addNeeds, int stCode) {
        assertThat(responseForPatchService.statusCode(), is(stCode));
        assertThat(responseForPatchService.body().jsonPath().get("totalprice"), is(equalTo(Integer.valueOf(totPr))));
        assertThat(responseForPatchService.body().jsonPath().getBoolean("depositpaid"), is(true));
        assertThat(responseForPatchService.body().jsonPath().get("bookingdates.checkin"), is(equalTo(checkin)));
        assertThat(responseForPatchService.body().jsonPath().get("bookingdates.checkout"), is(equalTo(cOut)));
        assertThat(responseForPatchService.body().jsonPath().get("additionalneeds"), is(equalTo(addNeeds)));
    }

    //    Get Method Section
    @When("i search with the id of a booked user with a GET method")
    public void iSearchWithTheIdOfABookedUserWithAGETMethod() {
        setHeadersWithManyHeaders();
        int bookingId = 31;
        setEndpointPath(restfulMakeAPostEndpoint + bookingId);
        getcall();
        responseForGetMethod = getResponse();
    }

    @Then("i should get the correct {string}, {string}, {string}, {string} and {string} returned with status code of {int}")
    public void iShouldGetTheCorrectAndReturnedWithStatusCodeOf(String fNam, String lNam, String totPr, String depPd, String chIn, int stCode) {
//        assertThat(responseForGetService.statusCode(),is(stCode));
        assertThat(responseForGetMethod.body().jsonPath().get("firstname"), is(equalTo(fNam)));
        assertThat(responseForGetMethod.body().jsonPath().get("lastname"), is(equalTo(lNam)));
        assertThat(responseForGetMethod.body().jsonPath().get("totalprice"), is(equalTo(Integer.valueOf(totPr))));
//        assertThat(responseForGetMethod.body().jsonPath().getBoolean("depositpaid"), is(equalTo(depPd)));
        assertThat(responseForGetMethod.body().jsonPath().get("depositpaid"), is(equalTo(Boolean.valueOf(depPd))));
        assertThat(responseForGetMethod.body().jsonPath().get("bookingdates.checkin"), is(equalTo(chIn)));
    }

}
