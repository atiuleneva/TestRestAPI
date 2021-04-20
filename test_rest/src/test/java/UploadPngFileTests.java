import org.atiuleneva.dto.ImageDataResponse;
import org.atiuleneva.utils.*;
import org.junit.jupiter.api.*;

import java.io.File;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadPngFileTests extends BaseTest {
    static private String uploadedImageId;
    static private String uploadedImageDeleteHash;
    static private String uploadedImageName;
    static private String uploadedImageDescription;

    @BeforeAll
    static void beforeAllUploadPngFileTests() {
    }

    @Test
    @Order(1)
    void UploadPngFileTest(){

        ImageDataResponse response =
                given()
                        .multiPart("image", new File(ImagePaths.IMAGE_PNG_FILE))
                        .multiPart("name", TestStrings.IMAGE_NAME)
                        .multiPart("description", TestStrings.IMAGE_DESCRIPTION)
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
        uploadedImageName = response.data.name;
        uploadedImageDescription = response.data.description;
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
    void ImageNameTest() {
        Assertions.assertEquals(TestStrings.IMAGE_NAME, uploadedImageName);
    }

    @Test
    @Order(5)
    void ImageDescriptionTest() {
        Assertions.assertEquals(TestStrings.IMAGE_DESCRIPTION, uploadedImageDescription);
    }

    @Test
    @Order(6)
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
        Assertions.assertEquals(Endpoints.LINK_IMAGE_URI_BASE + uploadedImageId + FileFormats.IMAGE_PNG, link);

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
