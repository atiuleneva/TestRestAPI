import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;

public class DelAlienJpgFromUrlTest extends BaseTest {
    static private String uploadedImageHash = "MMen98t";

    @Test
    void DeleteAlienImageTest()  {
        given()
                .headers(headers)
                .header("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/image/{imageDeleteHash}", uploadedImageHash)
                .prettyPeek()
                .then()
                .statusCode(403);
    }

    @AfterAll
    static void tearDown()  {
    }
}
