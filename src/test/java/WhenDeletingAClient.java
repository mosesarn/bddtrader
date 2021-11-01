import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.junit.Before;
import org.junit.Test;

public class WhenDeletingAClient {

    @Before
    public void setupBaseUrl(){
        RestAssured.baseURI ="http://localhost:9000/api";
    }

    @Test
    public void should_be_able_to_delete(){
        //Given a client exist
       // Client aNewClient = Client.withFirstName("Moses").andLastName("Arnold").andEmail("moses@scott.com");

       String id = aClientExists(Client.withFirstName("Moses").andLastName("Arnold").andEmail("moses@scott.com"));

        // When i delete the client
        RestAssured.given().delete("/client/{id}",id);
        //Then client no longer exists
        RestAssured.given()
                .get("/client/{id}",id)
                .then()
                .statusCode(404);
    }
    private static String aClientExists(Client aNewClient) {
        return RestAssured.given()
                 .contentType(ContentType.JSON)
                 .body(aNewClient)
                 .when()
                 .post("/client")
                .jsonPath().getString("id");
    }

}
