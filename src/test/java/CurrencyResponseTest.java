import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class CurrencyResponseTest {

    public static Response response;

    @Test
    public void getCurrencyResponseCodeTest(){
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT + Consts.TOKEN);
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes.USDEUR", notNullValue());
        response.then().body("quotes.USDNIS", notNullValue());
        response.then().body("quotes.USDRUB", notNullValue());

    }

    @ParameterizedTest
    @ValueSource (strings = {"USDEUR", "USDNIS","USDRUB","USDCAD"})
    public void getCurrenciesResponseTest(String currencies){
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT + Consts.TOKEN + Consts.AMPERSAND + Consts.SOURCE_DEFAULT + Consts.AMPERSAND + Consts.CURRENCIES_DEFAULT);
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes." + currencies, notNullValue());
    }

    @Test
    public void getCurrenciesResponseNegativeTest(){
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT + Consts.TOKEN + Consts.AMPERSAND + Consts.SOURCE_DEFAULT + Consts.AMPERSAND + "currencies=NIS");
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", containsString("You have provided one or more invalid Currency Code"));
    }

}
