import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class HistoricalResponseTest {

    public static Response response;

    @Test
    public void getHistoricalResponseCodeTest(){
        response = given().contentType("application/json").get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.AMPERSAND + Consts.DATE_DEFAULT + Consts.AMPERSAND + Consts.CURRENCIES_DEFAULT);
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo("2018-01-01"));
        response.then().body("timestamp", equalTo(1514851199));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDCAD", equalTo(1.25551f));
        response.then().body("quotes.USDEUR", equalTo(0.832296f));
        response.then().body("quotes.USDNIS", notNullValue());
        response.then().body("quotes.USDRUB", equalTo(57.980701f));
    }

    @Test
    public void getHistoricalEmptyResponseNegativeTest(){
        response = given().contentType("application/json").get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.AMPERSAND + "date=");
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(301));
        response.then().body("error.info", containsString("You have not specified a date"));
    }

    @Test
    public void getHistoricalInvalidDataResponseNegativeTest(){
        response = given().contentType("application/json").get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.AMPERSAND + "date=2018.21.23");
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(302));
        response.then().body("error.info", containsString("You have entered an invalid date"));
    }

    @Test
    public void getHistoricalInvalidCurrenciesResponseNegativeTest(){
        response = given().contentType("application/json").get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.AMPERSAND + Consts.DATE_DEFAULT + Consts.AMPERSAND + "currencies=NIS");
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", containsString("You have provided one or more invalid Currency Code"));
    }

}
