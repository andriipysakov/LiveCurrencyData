import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class CurrencyResponseTest {

    @Test
    public void getCurrencyResponseCodeTest(){
        Response response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT + Consts.TOKEN);
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes.USDEUR", notNullValue());
        response.then().body("quotes.USDNIS", notNullValue());
        response.then().body("quotes.USDRUB", notNullValue());

    }

    
}
