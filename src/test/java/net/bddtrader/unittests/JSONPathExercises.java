package net.bddtrader.unittests;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class JSONPathExercises {

    @Before
    public void prepare_rest_config(){
        baseURI = "https://bddtrader.herokuapp.com/api";
    }

    /**
     * Exercise 1:  read a single Field Value
     * complete the test shown below to read and verify the "industry" field foe AAPL
     * The Result should be 'Telecommunications Equipment'
     */
    @Test
    public void find_a_simple_field_value(){
        given().pathParam("stock-id", "AAPL")
                .when().get("stock/{stock-id}/company")
                .then().body("industry", equalTo("Telecommunications Equipment"));
    }
    /**
     * Exercise 2:  read a single Field Value
     * complete the test shown below to read and verify the "description" field for AAPL
     * The Result should be 'smartphonese'
     */
    @Test
    public void check_that_a_field_value_contains_a_given_string(){
        given().pathParam("stock-id", "AAPL")
                .when().get("stock/{stock-id}/company")
                .then().body("description", containsString("smartphones"));
    }
    /**
     * Example:3 Read a single nested field value.
     * Read the stock-id field in the 'quote' enter in the apple stock book.
     * The result should be 'APPL'
     */
    @Test
    public void find_the_nested_field_value(){
        given().pathParam("symbol", "AAPL")
                .when().get("stock/{symbol}/book")
                .then().body("quote.symbol",equalTo("AAPL"));
    }

    /**
     * Exercise 4: Find the list of values
     *Find the list of symbols recently traded from
     * https://bddtrader.herokuapp.com/api/tops/last  and
     * check the list contains "PTN", "PINE" and "TRS"
     */
    @Test
    public void find_a_list_of_values(){
        when().get("/tops/last")
                .then().body("symbol", hasItems("PTN","PINE","TRS"));
    }
    /**
     * Exercise 5: check that one item in the list matches a certain condition
     * check that one price greater than 100
     */
    @Test
    public void make_sure_at_least_one_item_matches_a_given_condition(){
        when().get("/tops/last")
                .then().body("price", hasItems(greaterThan(100.0f)));
    }
    /**
     * Exercise 6: check the value of a specific item in a list
     * check the price of the first trade in the apple stock book is 319.59
      */
    @Test
    public void first_trade_in_apple_trade_book(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/book")
                .then().body("trades[0].price",equalTo(319.59f));
    }
    /**
     * Example 7: check the value of a specific item in a list
     * find the field of the last element in a list 319.54f
     */
    @Test
    public void find_a_field_of_the_last_element(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/book")
                .then().body("trades[-1].price",equalTo(319.54f));
    }
    /**
     * Exercose 8 : check number of elements in a collection
     * check that there are 20 trades
     */
    @Test
    public void find_the_number_of_trades(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.size()",equalTo(20));
    }
    /**
     * Exercose 9 : check for minium or max
     * check that the minium trade in the apple boos is 319.38
     */
    @Test
    public void find_the_minium_trade_price(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/book")
                .then().body("trades.price.min()",equalTo(319.38f));
    }
    /**
     * Exercise 10:
     * check that the volume of the trade with minium price is 100
     */
    @Test
    public void find_the_size_of_the_trade_with_the_minimum_trade_price(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/book")
                // trades.min{it.price} wil do the same thing
                .then().body("trades.min{trade -> trade.price}.volume",equalTo(100f));
    }
    /**
     * Example 11 : find the collection of objects matching a specified criteria
     * check that there are 13 trades with prices grater than 319.50
     *
     */
    @Test
    public void fins_the_no_of_trades_with_a_price_greater_than_some_value(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/book")
                // trades.min{it.price} wil do the same thing
                .then().body("trades.findAll{it.price >319.50}.size()",equalTo(13));
    }
}

