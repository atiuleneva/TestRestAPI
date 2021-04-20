import org.atiuleneva.utils.Endpoints;
import org.atiuleneva.utils.TestStrings;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;

public class DelAlienJpgFromUrlTest extends BaseTest {

    @Test
    void DeleteAlienImageTest()  {
        given()
                .headers(headers)
                .spec(requestSpecification)
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, TestStrings.Alien_IMAGE_HASH)
                .prettyPeek()
                .then()
                .statusCode(403);
        ///Тестировала удаление чужой картинки с imgur, пришел ответ с кодом 200. Как такое получилось, если прав к доступу к данной картинке не имею. Наверное, я должна была получить код 403? У пользователя картинка осталась на его страничке, не удалилась. Возможно это баг?
    }

    @AfterAll
    static void tearDown()  {
    }
}
