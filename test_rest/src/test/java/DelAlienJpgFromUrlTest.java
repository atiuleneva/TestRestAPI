import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;

public class DelAlienJpgFromUrlTest extends BaseTest {
    static private String uploadedImageHash = "MMen98t";

    @Test
    void DeleteAlienImageTest()  {
        given()
                .headers(headers)
                .header("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/image/{imageDeleteHash}", uploadedImageHash)
                .prettyPeek()
                .then()
                .statusCode(403);
        ///Тестировала удаление чужой картинки с imgur, пришел ответ с кодом 200. Как такое получилось, если прав к доступу к данной картинке не имею. Наверное, я должна была получить код 403? У пользователя картинка осталась на его страничке, не удалилась. Возможно это баг?
    }

    @AfterAll
    static void tearDown()  {
    }
}
