import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.File;


import static io.restassured.RestAssured.given;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadEmptyFileJpg extends BaseTest {
    static final String FILE_PATH = "src/test/resources/emptyFile.jpg";
    static private int uploadEmpty;

    @BeforeAll
    static void beforeAllUploadEmptyFileTest() {
    }

    @Test
    @Order(1)
    void UploadEmptyFileTest(){
        Response response =
        given()
                .multiPart("image", new File(FILE_PATH))
                .header("Authorization", token)
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        uploadEmpty= response.jsonPath().getInt("data.error.code");

    }

    @AfterAll
    static void tearDown()  {

    }
}
