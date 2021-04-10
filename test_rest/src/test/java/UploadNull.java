import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
                        .header("Authorization", token)
                        .when()
                        .post("https://api.imgur.com/3/image")
                        .prettyPeek()
                        .then()
                        .statusCode(400)
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();


    }

    @AfterAll
    static void tearDown()  {

    }
}

