import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
public class WhenCreatingANewClient {

    // Note:- correct one  run this command in terminal before executing this test.  mvn spring-boot:run
    // Note:- just for data run this command in terminal before executing this test.  mvn spring-boot:run -Ddata.source=IEX
    @Before
    public void setupBaseUrl(){
        RestAssured.baseURI ="http://localhost:9000/api";
    }

    @Test
    public void map_each_new_client_should_get_a_new_id(){
        // Method 2: Instead of passing a JSON String we can use the client class using the builder pattern.
       //  Client aNewClient = Client.withFirstName("Moses").andLastName("Arnold").andEmail("moses@scott.com");

        //Method 3: using a Map
        Map<String, Object> clientData = new HashMap<>();
        clientData.put("email","moses@scott.com");
        clientData.put("firstName","Moses");
        clientData.put("lastName","Arnold");

        /** Method 1:
         *  String newclient = "{\n" +
         *                 "\"email\":\"moses@scott.com\",\n" +
         *                 "\"firstName\":\"moses\",\n" +
         *                 "\"lastName\":\"arnold\",\n" +
         *                 "}";
          */


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(clientData)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("email", equalTo("moses@scott.com"))
                .and().body("firstName", equalTo("Moses"))
                .and().body("lastName", equalTo("Arnold"));

    }
    @Test
    public void client_class_each_new_client_should_get_a_new_id(){
        // Method 2: Instead of passing a JSON String we can use the client class using the builder pattern.
         Client aNewClient = Client.withFirstName("Moses").andLastName("Arnold").andEmail("moses@scott.com");


        /** Method 1:
         *  String newclient = "{\n" +
         *                 "\"email\":\"moses@scott.com\",\n" +
         *                 "\"firstName\":\"moses\",\n" +
         *                 "\"lastName\":\"arnold\",\n" +
         *                 "}";
         */


        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(aNewClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("email", equalTo("moses@scott.com"))
                .and().body("firstName", equalTo("Moses"))
                .and().body("lastName", equalTo("Arnold"));

    }

}
