package apiPackage;

import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class SampleAPITestUsingRa {
    @Test
    public void GetCommentForJasonPlaceHolder() {
        given().contentType(ContentType.JSON).log().all().
//                when().get("https://jsonplaceholder.typicode.com/comments/5").
        when().get("https://jsonplaceholder.typicode.com/comments/{Id}", 5).
                then().statusCode(200).log().all().body("name", equalTo("vero eaque aliquid doloribus et culpa")).
                body("email", equalTo("Hayden@althea.biz"));

    }

    @Test
    public void PostACommentForJasonPlaceHolder() {
        HashMap<String, String> postComment = new HashMap<>();
        postComment.put("userId", "4");
        postComment.put("title", "Comment on pictures that I Like");
        postComment.put("email", "lateef.abdulsalam@ayana.info");
        postComment.put("body", "I like the post by my friend put out yesterday.....");

        given().contentType(ContentType.JSON).accept(ContentType.JSON).log().all().with().body(postComment).
                when().post("https://jsonplaceholder.typicode.com/comments/").
                then().statusCode(201).log().all().body("title", equalTo("My Holiday to Texas")).
                body("userId", equalTo("3")).
                body("body", equalTo("I went on holiday to Houston Texas and i enjoyed it so much with my family. Here are my photos"));

    }

    @Test
    public void PartiallyUpdateAComment() {
        HashMap<String, String> partlyUpdateComment = new HashMap<>();
        partlyUpdateComment.put("name", "BabaOzil");
        partlyUpdateComment.put("email", "Babaozil@gardner.biz");
        partlyUpdateComment.put("body", "for it is as if it were a great pleasure indeed for those who praise Him in which the pain of the necessities is as it were of those who are reciting and for the wise accusing Him. Up Gunners");

        given().contentType(ContentType.JSON).accept(ContentType.JSON).log().all().with().body(partlyUpdateComment).
                when().patch("https://jsonplaceholder.typicode.com/comments/{Id}", 501).
                then().statusCode(200).body("name", equalTo("BabaOzil")).
                body("email", equalTo("Babaozil@gardner.biz")).log().all();
    }

    @Test
    public void UpdateACommentOrUpdateAPost() {
        HashMap<String, String> postComment = new HashMap<>();
        postComment.put("userId", "3");
        postComment.put("title", "My Holiday to Texas");
        postComment.put("body", "I went on holiday to Houston Texas and i enjoyed it so much with my family. Here are my photos");

        given().contentType(ContentType.JSON).accept(ContentType.JSON).log().all().with().body(postComment).
                when().put("https://jsonplaceholder.typicode.com/posts/{Id}", 3).
                then().statusCode(200).body("userId", equalTo("3")).
                body("title", equalTo("My Holiday to Texas")).
                body("body", equalTo("I went on holiday to Houston Texas and i enjoyed it so much with my family. Here are my photos")).log().all();
    }

@Test
    public void createAToken(){
        HashMap<String, String> createToken = new HashMap<>();
        createToken.put("username", "admin");
        createToken.put("password", "password123");

        given().contentType(ContentType.JSON).with().body(createToken).log().all().
                when().post("https://restful-booker.herokuapp.com/auth").
                then().log().all().body("token", equalTo("f44c2cbf7fc1467"));
    }

@Test
    public void createABooking(){
        HashMap<String, String> createBooking = new HashMap<>();
        createBooking.put("firstname", "Mustee");
        createBooking.put("lastname", "Adu");
        createBooking.put("totalprice", "800");
        createBooking.put("depositpaid", "false");
        createBooking.put("checkin", "2022-03-03");
        createBooking.put("checkout", "2022-03-12");

        given().contentType(ContentType.JSON).accept(ContentType.JSON).log().all().with().body(createBooking).
                when().post("https://restful-booker.herokuapp.com/booking").
                then().statusCode(200).log().all().body("firstname", equalTo("Mustee")).
                body("lastname", equalTo("Adu")).
                body("totalprice", equalTo(800)).
                body("depositpaid", equalTo("false")).
                body("bookingid", equalTo(14)).
                body("checkout", equalTo("2022-03-12"));
    }

    @Test
    public void updateABooking() {
        HashMap<String, String> updateBooking = new HashMap<>();
        updateBooking.put("firstname", "Mustapha");
        updateBooking.put("lastname", "Adu");
        updateBooking.put("totalprice", "900");
        updateBooking.put("depositpaid", "true");
        updateBooking.put("checkin", "2022-03-10");
        updateBooking.put("checkout", "2022-03-12");

        given().contentType(ContentType.JSON).accept(ContentType.JSON).header("authorization ",  "Basic YWRtaW46cGFzc3dvcmQxMjM=").log().all().with().body(updateBooking).
                when().put("https://restful-booker.herokuapp.com/booking/{Id}", 21).
                then().statusCode(200).log().all().body("firstname", equalTo("Mustapha")).
                body("lastname", equalTo("Adu")).
                body("totalprice", equalTo(900)).
                body("depositpaid", equalTo("true")).
                body("checkout", equalTo("2022-03-12"));
    }

    @Test
    public void SearchOpenWeatherByCityName(){
        given().log().all().
                when().get("api.openweathermap.org/api.openweathermap.org/data/").
                then().statusCode(200) .log().all().body("base", equalTo("stations"));
    }
}
