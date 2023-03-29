import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SecurityTest {


    @ParameterizedTest
    @ValueSource (strings = {Consts.LIVE_ENDPOINT, Consts.HISTORICAL_ENDPOINT})
    public void invalidTokenTest(String endpoints) {
        Response response = given().contentType("application/json").get(Consts.URL + endpoints + "test");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", equalTo("You have not supplied a valid API Access Key"));
    }

}
