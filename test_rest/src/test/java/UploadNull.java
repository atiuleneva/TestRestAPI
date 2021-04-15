import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.atiuleneva.utils.Endpoints;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;


public class UploadNull extends BaseTest{

    @BeforeAll
    static void beforeAllUploadNullTests() {
    }

    @Test
    void UploadNullTest(){
        Response response =
                given()
                        .multiPart("image", "")
                        .spec(requestSpecification)
                        .when()
                        .post(Endpoints.POST_IMAGE_REQUEST)
                        .prettyPeek()
                        .then()
                        .spec(responseSpecification400)
                        .extract()
                        .response();


    }

    @AfterAll
    static void tearDown()  {

    }
}

