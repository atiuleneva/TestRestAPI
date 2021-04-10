import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.regex.Pattern;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadJpgBigNameFileTests extends BaseTest {
    static final String FILE_PATH = "src/test/resources/baby-yoda-baby-yoda-the-mandalorian-mandalorian-green-cute-s.jpg";
    static String imgName = "The first season of Parks and Recreation originally aired in the United States on the NBC television network between April 9 and May 14, 2009. Produced by Deedle-Dee Productions and Universal Media Studios, the series was created by Greg Daniels and Michael Schur, who served as executive producers with Howard Klein. The season stars Amy Poehler, Rashida Jones, Paul Schneider, Aziz Ansari, Nick Offerman, and Aubrey Plaza.\n" +
            "\n" +
            "The comedy series focuses on Leslie Knope (Poehler), the deputy director of the Parks and Recreation Department of the fictional town of Pawnee, Indiana. The season consisted of six 22-minute episodes, all of which aired at 8:30 p.m. on Thursdays. Daniels and Schur conceived the show when NBC officials asked Daniels to produce a spin-off of his comedy series The Office, on which Schur was a writer. During development, the creators decided the new show would be a stand-alone series, though it would share the mockumentary style of The Office. Like that show, Parks and Recreation encouraged improvisation among its cast members.\n" +
            "\n" +
            "Early test screenings were poor, and many critics and industry observers were skeptical about the show's chances of success. The first season received generally mixed reviews, and several commentators found it too similar to The Office. The premiere episode was watched by 6.77 million viewers, but the viewership declined almost every week in the Nielsen ratings. A season low of 4.25 million viewers watched the final episode, \"Rock Show\". Despite the low rating, \"Rock Show\" received the best reviews of the season and convinced some critics that the series had finally found the right tone.";
    static private String uploadedImageId;
    static private String uploadedImageDeleteHash;
    static private String uploadedImageName;

    @BeforeAll
    static void beforeAllUploadBigNameFileTests() {
    }

    @Test
    @Order(1)
    void UploadBigNameFileTest(){

        Response response =
                given()
                        .multiPart("image", new File(FILE_PATH))
                        .multiPart("name", imgName)
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
