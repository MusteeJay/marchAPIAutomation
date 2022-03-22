package utilities;

import com.jayway.jsonpath.DocumentContext;

public class RequestBodyService {
    public void SetRequestBodyForComment(DocumentContext requestBody, String postId, String name, String email, String body){
        requestBody.set("postId", postId);
        requestBody.set("name", name);
        requestBody.set("email", email);
        requestBody.set("body", body);
    }

    public void SetRequestBodyForPost(DocumentContext requestBody, String userId, String title, String body){
        requestBody.set("userId", userId);
        requestBody.set("title", title);
        requestBody.set("body", body);
    }

    public void SetRequestBodyForUpdate(DocumentContext requestBody, String userId, String title, String body){
        requestBody.set("userId", userId);
        requestBody.set("title", title);
        requestBody.set("body", body);
    }

    public void SetRequestBodyForUser(DocumentContext requestBody, String name, String username, String street,
                                      String suite, String phone, String catchPhrase){
        requestBody.set("name", name);
        requestBody.set("username", username);
        requestBody.set("address.street", street);
        requestBody.set("address.suite", suite);
        requestBody.set("phone", phone);
        requestBody.set("company.catchPhrase", catchPhrase);

    }
}
