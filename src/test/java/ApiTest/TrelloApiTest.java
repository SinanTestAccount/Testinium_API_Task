package ApiTest;

import Utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class TrelloApiTest {


    String idBoard;
    String idList;
    String idCart1;
    String idCart2;


    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("baseURI");
    }

    @Test(priority = 1)
    public  void  CreateBoard(){

        Response response = given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("name","newBoard")
                .when().post("1/boards/");
        response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertEquals(response.path("name"),"newBoard");

        idBoard=response.path("id");

    }
    @Test(priority = 2)
    public  void  CreateCards(){

        Response response = given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("name","newList")
                .queryParam("idBoard",idBoard)
                .when().post("1/lists");
        response.prettyPrint();

        System.out.println("New List Created");

        idList=response.path("id");

                response = given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("idList",idList)
                .when().post("1/cards/");
        response.prettyPrint();
        idCart1=response.path("id");
        assertEquals(response.statusCode(),200);

        System.out.println("New Card Created");

        response = given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("idList",idList)
                .when().post("1/cards/");
        response.prettyPrint();
        idCart2=response.path("id");
        assertEquals(response.statusCode(),200);

        System.out.println("New Card Created");

    }
    @Test(priority = 3)
    public  void  UpdateCard(){

        Response response = given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("id",idCart1)
                .queryParam("name","UpdatedCardName")
                .and().pathParam("id",idCart1)
                .when().put("1/cards/{id}");
        response.prettyPrint();

        assertEquals(response.statusCode(),200);
        System.out.println("Card Updated");

    }

    @Test(priority = 4)
    public  void  DeleteCards(){

             Response response=   given()
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("id",idCart1)
                .and().pathParam("id",idCart1)
                .when().delete("1/cards/{id}");
        assertEquals(response.statusCode(),200);

                given()
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("id",idCart2)
                .and().pathParam("id",idCart2)
                .when().delete("1/cards/{id}");
        assertEquals(response.statusCode(),200);
        System.out.println("Cards Deleted");

    }
    @Test(priority = 5)
    public void DeleteBoard(){

        Response response = given()
                .queryParam("key","80ab52f3e5a7bb18bc0bd1b5f9459be4")
                .queryParam("token","053eefa4abee2df0e3f48d3ecb7eccdbbe69ae8e95389c3b548ef1483789e3e5")
                .queryParam("id",idBoard)
                .and().pathParam("id",idBoard)
                .when().delete("1/boards/{id}");
        assertEquals(response.statusCode(),200);

        System.out.println("Board Deleted");

    }
}