package qa.quru.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;
import static qa.quru.helpers.CustomApiListener.withCustomTemplates;

public class SingleUserSpecs {
    public static RequestSpecification singleUserRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/users/{userId}")
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);

    public static ResponseSpecification singleUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("data", notNullValue())
            .build();

    public static ResponseSpecification errorResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("data", nullValue())
            .build();
}
