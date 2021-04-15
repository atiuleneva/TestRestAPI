import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.atiuleneva.dto.ImageDataResponse;
import org.atiuleneva.utils.Endpoints;
import org.atiuleneva.utils.ImagePaths;
import org.junit.jupiter.api.*;

import java.io.File;


import static io.restassured.RestAssured.given;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadEmptyFileJpg extends BaseTest {
    static private int uploadEmpty;

    @BeforeAll
    static void beforeAllUploadEmptyFileTest() {
    }

    @Test
    @Order(1)
    void UploadEmptyFileTest(){
        ImageDataResponse response =
        given()
                .multiPart("image", new File(ImagePaths.IMAGE_EMPTY))
                .spec(requestSpecification)
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .spec(responseSpecification400)
                .extract()
                .body()
                .as(ImageDataResponse.class);

        Assertions.assertEquals(1003, response.data.error.code);

    }

    @AfterAll
    static void tearDown()  {

    }
}
