import org.atiuleneva.utils.Endpoints;
import org.atiuleneva.utils.ImagePaths;
import org.junit.jupiter.api.*;
import java.io.File;
import static io.restassured.RestAssured.given;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadJpgFileBigSizeTests extends BaseTest {


    @BeforeAll
    static void beforeAllUploadJpgFileBigSizeTests() {
    }

    @Test
    @Order(1)
    void UploadPngFileTest(){
        given()
                .multiPart("image", new File(ImagePaths.IMAGE_BIG_SIZE))
                .spec(requestSpecification)
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .spec(responseSpecification400);
    }

    @AfterAll
    static void tearDown()  {

    }
}
