package qa.quru.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.with;
import static qa.quru.helpers.CustomApiListener.withCustomTemplates;

public class RegistrationSpecs {
    public static RequestSpecification registrationRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/register")
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);

    public static ResponseSpecification registrationResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("token", notNullValue())
            .expectBody("id", notNullValue())
            .build();

    public static ResponseSpecification errorResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("error", notNullValue())
            .build();
}
