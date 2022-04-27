import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class BillTest {
    public String token = "";
    File newrequest = new File("C:\\Users\\agajendra\\Desktop\\API_TESTING\\API_Testing\\newrequest.json");
    File postrequest = new File("C:\\Users\\agajendra\\Desktop\\API_TESTING\\API_Testing\\postrequest.json");
    @BeforeTest
    public void getting_token() throws IOException
    {
        token = BaseUtilities.test_get_token();
    }

    @Test(priority = 1)
    public void Get_All_Bills(){
        try{

            Response response =
                    given()
                            .baseUri(BaseUtilities.url).
                            header("Authorization",
                                    "Bearer " + token).header("content-type","application/json")
                            .when()
                            .get("/project")
                            .then()
                            .statusCode(200)
                            .extract().response();
            if (response.getBody().asString() != null)
            {
                System.out.println(response.getBody().asString());
            }
            int length = response.jsonPath().getList("billId").size();
            System.out.println(length);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 2)
    public void Get_Bill_By_id(){
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .get("/bill/69")
                    .then()
                    .statusCode(200).body("billId",equalTo(69))
                    .extract().response();
            if (response.statusCode() == 200)
            {
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 3)
    public void Paid_bills(){
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .get("/totalMoney/3/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null)
            {
                System.out.println(response.getBody().asString());
            }
            else
            {
                System.out.println("Problem detected");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 4)
    public void New_Request(){
        try
        {
            Response response = given()
                    .baseUri(BaseUtilities.url).
                    body(newrequest).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .put("/bill/69")
                    .then()
                    .statusCode(200).body("billId",equalTo(69))
                    .extract().response();
            if (response.statusCode() == 200)
            {
                Assert.assertEquals(response.statusCode(),200);
            }
            else
            {
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 5)
    public void Send_mail_to_Client(){
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    body(newrequest).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .put("/bill/69")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 6)
    public void Change_Status(){
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    body(newrequest).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .put("/bill/69")
                    .then()
                    .statusCode(200).body("billId",equalTo(69))
                    .extract().response();
            if (response.statusCode() == 200)
            {
                Assert.assertEquals(response.statusCode(),200);
            }
            else
            {
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /*@Test(priority = 7)
    public void Delete_Request(){
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    body(newrequest).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .delete("/bill/76")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }*/

    @Test(priority = 8)
    public void Post_Request(){
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    body(postrequest).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .post("/bill/3/2022/65")
                    .then()
                    .statusCode(204).body("billId",equalTo(65))
                    .extract().response();
            if (response.statusCode() == 204)
            {
                Assert.assertEquals(response.statusCode(),204);
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }



}
