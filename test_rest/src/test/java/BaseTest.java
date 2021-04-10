import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class BaseTest {
    protected static Map<String, String> headers = new HashMap<>();
    protected static String token;
    protected static String username;
    static Properties properties = new Properties();

    @BeforeAll
    static void beforeAll() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        token = properties.getProperty("token");
        username = properties.getProperty("username");
        RestAssured.baseURI = properties.getProperty("base.url");
        RestAssured.defaultParser = Parser.JSON;
    }
}
