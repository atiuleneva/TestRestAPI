import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.atiuleneva.dto.ImageDataResponse;
import org.atiuleneva.utils.Endpoints;
import org.atiuleneva.utils.FileFormats;
import org.atiuleneva.utils.ImagePaths;
import org.atiuleneva.utils.RegExpSet;
import org.junit.jupiter.api.*;
import java.util.regex.Pattern;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadPngFromUrlTests extends BaseTest {
    static private String uploadedImageId;
    static private String uploadedImageDeleteHash;

    @BeforeAll
    static void beforeAllUploadPngFromUrlTests() {
    }

    @Test
    @Order(1)
    void UploadPngFromUrlTest(){

        ImageDataResponse response =
                given()
                        .multiPart("image", ImagePaths.IMAGE_PNG_URL)
                        .spec(requestSpecification)
                        .when()
                        .post(Endpoints.POST_IMAGE_REQUEST)
                        .prettyPeek()
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .body()
                        .as(ImageDataResponse.class);

        uploadedImageId = response.data.id;
        uploadedImageDeleteHash = response.data.deletehash;

    }

    @Test
    @Order(2)
    void ImageIdFormatTest() {
        Assertions.assertTrue(RegExpSet.ImageHash.matcher(uploadedImageId).matches());
    }

    @Test
    @Order(3)
    void ImageDeleteHashFormatTest() {
        Assertions.assertTrue(RegExpSet.ImageDeleteHash.matcher(uploadedImageDeleteHash).matches());
    }


    @Test
    @Order(4)
    void GetImageTest(){
        ImageDataResponse response =
                given()
                        .headers(headers)
                        .spec(requestSpecification)
                        .when()
                        .get(Endpoints.GET_IMAGE_REQUEST, uploadedImageId)
                        .prettyPeek()
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .body()
                        .as(ImageDataResponse.class);

        String link = response.data.link;
        Assertions.assertEquals(Endpoints.LINK_IMAGE_URI_BASE + uploadedImageId + FileFormats.IMAGE_JPG, link);

    }

    @AfterAll
    static void tearDown()  {
        given()
                .headers(headers)
                .spec(requestSpecification)
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, uploadedImageDeleteHash)
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

}
