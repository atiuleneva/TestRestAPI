import org.atiuleneva.dto.ImageDataResponse;
import org.atiuleneva.utils.Endpoints;
import org.atiuleneva.utils.ImagePaths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadTxtFileTests extends BaseTest{

    @BeforeAll
    static void beforeAllUploadTxtFileTests() {
    }

    @Test
    void UploadTxtFileTest(){
        ImageDataResponse response =
                given()
                        .multiPart("image", new File(ImagePaths.FILE_TXT))
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
