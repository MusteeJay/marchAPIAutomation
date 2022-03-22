package utilities;

import com.jayway.jsonpath.DocumentContext;

public class RestfulRequestBodyService{
    public void SetRestfulRequestBodyForPost(DocumentContext requestBody, String firstname, String lastname, String totalprice,
                                             String depositpaid, String checkin, String checkout){
        requestBody.set("firstname", firstname);
        requestBody.set("lastname", lastname);
        requestBody.set("totalprice", totalprice);
        requestBody.set("depositpaid", depositpaid);
        requestBody.set("bookingdates.checkin", checkin);
        requestBody.set("bookingdates.checkout", checkout);
    }
    public void SetRestfulRequestBodyForUpdate(DocumentContext requestBody, String totalprice, String depositpaid,
                                               String checkin, String checkout, String additionalneeds){
        requestBody.set("totalprice", totalprice);
        requestBody.set("depositpaid", depositpaid);
        requestBody.set("bookingdates.checkin", checkin);
        requestBody.set("bookingdates.checkout", checkout);
        requestBody.set("additionalneeds", additionalneeds);

    }
    public void SetRestfulResponseBodyGetUserID(DocumentContext responseBodyForCreate, String bookingID){
        responseBodyForCreate.set("bookingid", bookingID);
    }
    public void setRestfulRequestBodyCreateToken(DocumentContext requestBody, String username, String password){
        requestBody.set("username", username);
        requestBody.set("password", password);
    }

}
