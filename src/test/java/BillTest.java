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
    File newrequest = new File("C:\\Users\\adityakumar3\\API_Testing\\new_request.json");
    File postrequest = new File("C:\\Users\\adityakumar3\\API_Testing\\postrequest.json");


    @BeforeTest
    public void Get_Bearer_Token() throws IOException {
         token = BaseUtilities.test_get_token();
    }


    @Test(priority = 20)
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

    @Test(priority = 21)
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

    @Test(priority = 22)
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

    @Test(priority = 23)
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

    @Test(priority = 24)
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

    @Test(priority = 25)
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


    @Test(priority = 26)
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
