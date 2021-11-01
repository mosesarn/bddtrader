package net.bddtrader.unittests;

import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;

public class WhenGettingCompanyDetails {
    @Before
    public void prepare_rest_config(){
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api";
    }

    @Test
    public void should_return_name_and_sector(){

        RestAssured.given()
                .pathParam("symbol", "AAPL")
        .get("/stock/{symbol}/company")
                .then()// optional
                .body("companyName", Matchers.equalTo("Apple, Inc."))
                .body("sector", Matchers.equalTo("Electronic Technology"));

    }
    @Test
    public void should_return_news_for_a_company(){
          // REquest URL: https://bddtrader.herokuapp.com/api/news?symbols=fb

        RestAssured.given()
                .queryParam("symbols", "fb")
                .when()
                .get("/news")
                .then()// optional
                .body("related", everyItem(containsString("FB")))
                ;


    }
    @Test
    public void should_return_name_and_selector_from_locale(){
       // GPath REference Link:  http://groovy-lang.org/processing-xml.html#_gpath
//        // Note:- run this command in terminal before executing this test.  mvn spring-boot:run -Ddata.source=IEX
//        RestAssured.get("http://localhost:9000/api/stock/AAPL/company")
//        .then()
//                .body("companyName", Matchers.equalTo("Apple, Inc."))
//                .body("sector", Matchers.equalTo("Electronic Technology"));
    }
}
