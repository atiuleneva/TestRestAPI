import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.regex.Pattern;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadPngFromUrlTests extends BaseTest {
    static final String url = "https://static.wikia.nocookie.net/rustarwars/images/d/d6/Yoda_SWSB.png";
    static private String uploadedImageId;
    static private String uploadedImageDeleteHash;

    @BeforeAll
    static void beforeAllUploadPngFromUrlTests() {
    }

    @Test
    @Order(1)
    void UploadPngFromUrlTest(){

        Response response =
                given()
                        .multiPart("image", url)
                        .header("Authorization", token)
                        .when()
                        .post("https://api.imgur.com/3/image")
                        .prettyPeek()
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        uploadedImageId = response.jsonPath().getString("data.id");
        uploadedImageDeleteHash = response.jsonPath().getString("data.deletehash");

    }

    @Test
    @Order(2)
    void ImageIdFormatTest() {
        Assertions.assertTrue(
                Pattern.compile("^[a-zA-Z0-9]{7}$").matcher(uploadedImageId).matches());
    }

    @Test
    @Order(3)
    void ImageDeleteHashFormatTest() {
        Assertions.assertTrue(
                Pattern.compile("^[a-zA-Z0-9]{15}$").matcher(uploadedImageDeleteHash).matches());
    }


    @Test
    @Order(4)
    void GetImageTest(){
        Response response =
                given()
                        .headers(headers)
                        .header("Authorization", token)
                        .when()
                        .get("https://api.imgur.com/3/image/{imageId}", uploadedImageId)
                        .prettyPeek()
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String link = response.jsonPath().getString("data.link");
        Assertions.assertEquals("https://i.imgur.com/" + uploadedImageId + ".jpg", link);

    }

    @AfterAll
    static void tearDown()  {
        given()
                .headers(headers)
                .header("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/image/{imageDeleteHash}", uploadedImageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}
