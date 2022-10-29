package qa.quru.reqresIn;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static qa.quru.specs.UserListSpec.userListRequestSpec;
import static qa.quru.specs.UserListSpec.userListResponseSpec;

public class UserListTests {
    @Test
    public void checkUserEmailsTest() {
        given()
                .spec(userListRequestSpec)
                .when()
                .get()
                .then()
                .spec(userListResponseSpec)
                .body("data.email",
                        everyItem(endsWith("@reqres.in")));
    }

    @Test
    public void checkAvatarForUserTest() {
        given()
                .spec(userListRequestSpec)
                .queryParam("page", 2)
                .when()
                .get()
                .then()
                .spec(userListResponseSpec)
                .body("data.findAll{it.id == 10}.avatar",
                        hasItem("https://reqres.in/img/faces/10-image.jpg"));
    }
}
