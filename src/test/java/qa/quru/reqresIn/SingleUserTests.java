package qa.quru.reqresIn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import qa.quru.models.SingleUserResponsePojoModel;
import qa.quru.models.UserDataResponsePojoModel;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static qa.quru.specs.SingleUserSpecs.*;

public class SingleUserTests {
    @Test
    public void singleUserSuccessfulTest() {
        int userId = 2;

        SingleUserResponsePojoModel response = given()
                .spec(singleUserRequestSpec)
                .pathParam("userId", userId)
                .when()
                .get()
                .then()
                .spec(singleUserResponseSpec)
                .extract()
                .as(SingleUserResponsePojoModel.class);

        UserDataResponsePojoModel userData = response.getData();

        assertThat(userData.getId()).isEqualTo(userId);
        assertThat(userData.getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(userData.getFirstName()).isEqualTo("Janet");
        assertThat(userData.getLastName()).isEqualTo("Weaver");
        assertThat(userData.getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }

    @ValueSource(strings = {"22", "one"})
    @ParameterizedTest
    public void singleUserNotFoundTest(String userId) {
        given()
                .spec(singleUserRequestSpec)
                .pathParam("userId", userId)
                .when()
                .get()
                .then()
                .spec(errorResponseSpec);
    }
}
