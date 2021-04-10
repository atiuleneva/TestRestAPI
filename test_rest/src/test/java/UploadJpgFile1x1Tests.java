import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.regex.Pattern;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadJpgFile1x1Tests extends BaseTest {
    static final String FILE_PATH = "src/test/resources/baby-yoda-baby-yoda-the-mandalorian-mandalorian-green-cute-s.jpg";
    static String imgName = "Baby Yoda";
    static String imgDesc = "The Mandalorian";
    static private String uploadedImageId;
    static private String uploadedImageDeleteHash;
    static private String uploadedImageName;
    static private String uploadedImageDescription;

    @BeforeAll
    static void beforeAllUploadJpgFile1x1Tests() {
    }

    @Test
    @Order(1)
    void UploadJpgFile1x1Tests(){

        Response response =
                given()
                        .multiPart("image", new File(FILE_PATH))
                        .multiPart("name", imgName)
                        .multiPart("description", imgDesc)
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
        uploadedImageName = response.jsonPath().getString("data.name");
        uploadedImageDescription = response.jsonPath().getString("data.description");
    }

    @Test
    @Order(2)
    void TestImageIdFormat() {
        Assertions.assertTrue(
                Pattern.compile("^[a-zA-Z0-9]{7}$").matcher(uploadedImageId).matches());
    }

    @Test
    @Order(3)
    void TestImageDeleteHashFormat() {
        Assertions.assertTrue(
                Pattern.compile("^[a-zA-Z0-9]{15}$").matcher(uploadedImageDeleteHash).matches());
    }

    @Test
    @Order(4)
    void TestImageName() {
        Assertions.assertEquals(imgName, uploadedImageName);
    }

    @Test
    @Order(5)
    void TestImageDescription() {
        Assertions.assertEquals(imgDesc, uploadedImageDescription);
    }

    @Test
    @Order(6)
    void GetTestImage(){
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
