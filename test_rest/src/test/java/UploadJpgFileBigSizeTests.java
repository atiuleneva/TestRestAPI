import org.junit.jupiter.api.*;
import java.io.File;
import static io.restassured.RestAssured.given;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadJpgFileBigSizeTests extends BaseTest {
    static final String FILE_PATH = "src/test/resources/0D6A3458.jpg";


    @BeforeAll
    static void beforeAllUploadJpgFileBigSizeTests() {
    }

    @Test
    @Order(1)
    void UploadPngFileTest(){
        given()
                .multiPart("image", new File(FILE_PATH))
                .header("Authorization", token)
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @AfterAll
    static void tearDown()  {

    }
}
