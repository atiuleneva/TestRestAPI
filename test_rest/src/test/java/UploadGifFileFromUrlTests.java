import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.regex.Pattern;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadGifFileFromUrlTests extends BaseTest {
    static final String url = "https://peopletalk.ru/wp-content/uploads/2020/01/giphy-8.gif";
    static String imgName = "Baby Yoda";
    static String imgDesc = "The Mandalorian";
    static private String uploadedImageId;
    static private String uploadedImageDeleteHash;
    static private String uploadedImageName;
    static private String uploadedImageDescription;
    static private String uploadedImageLink;
    static private String uploadedImageMp4;
    static private String uploadedImageGifv;
    static private String uploadedImageHls;


    @BeforeAll
    static void beforeAllUploadGifFileFromUrlTests() {
    }

    @Test
    @Order(1)
    void UploadGifFileFromUrlTest(){

        Response response =
                given()
                        .multiPart("image", url)
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
    void ImageNameTest() {
        Assertions.assertEquals(imgName, uploadedImageName);
    }

    @Test
    @Order(5)
    void ImageDescriptionTest() {
        Assertions.assertEquals(imgDesc, uploadedImageDescription);
    }

    @Test
    @Order(6)
    void GetImageTest() {
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

        uploadedImageLink = response.jsonPath().getString("data.link");
        uploadedImageMp4 = response.jsonPath().getString("data.mp4");
        uploadedImageGifv = response.jsonPath().getString("data.gifv");
        uploadedImageHls = response.jsonPath().getString("data.hls");
    }


    @Test
    @Order(7)
    void ImageLinkTest(){
        Assertions.assertEquals("https://i.imgur.com/" + uploadedImageId + ".gif", uploadedImageLink);
    }

    @Test
    @Order(8)
    void ImageMp4Test() {
        Assertions.assertEquals("https://i.imgur.com/" + uploadedImageId + ".mp4", uploadedImageMp4);
    }

    @Test
    @Order(9)
    void ImageGifvTest(){
        Assertions.assertEquals("https://i.imgur.com/" + uploadedImageId + ".gifv", uploadedImageGifv);
    }

    @Test
    @Order(10)
    void ImageHlsTest(){
        Assertions.assertEquals("https://i.imgur.com/" + uploadedImageId + ".m3u8", uploadedImageHls);
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
