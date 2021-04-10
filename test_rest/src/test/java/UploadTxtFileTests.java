import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadTxtFileTests extends BaseTest{
    static final String FILE_PATH = "src/test/resources/HelloWorld.txt";
    static private int uploadTxt;

    @BeforeAll
    static void beforeAllUploadTxtFileTests() {
    }

    @Test
    void UploadTxtFileTest(){
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

        uploadTxt= response.jsonPath().getInt("data.error.code");

    }

    @AfterAll
    static void tearDown()  {

    }

}
