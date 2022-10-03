package qa.quru.reqresIn;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SingleUserTests {
    String baseUrl = "https://reqres.in/api/users/{userId}";

    @Test
    public void singleUserSuccessfulTest() {
        int userId = 2;

        given()
                .when()
                .get(baseUrl, userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(userId));
    }

    @Test
    public void singleUserNotFoundTest() {
        int userId = 22;

        given()
                .when()
                .get(baseUrl, userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void singleUserNotFoundByStringTest() {
        String userId = "one";

        given()
                .when()
                .get(baseUrl,userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
