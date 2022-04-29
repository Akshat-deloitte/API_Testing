import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class BillTest {
    public String token = "";

    File newrequest = new File("C:\\Users\\akashyab\\Desktop\\APITesting_ClientBilling\\API_Testing\\newrequest.json");
    File postrequest = new File("C:\\Users\\akashyab\\Desktop\\APITesting_ClientBilling\\API_Testing\\postrequest.json");
    private  static final String LOG_FILE = "log4j.properties";
    private static Logger log  = LogManager.getLogger(LoginTest.class);





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
                log.info("Getting all Bills");
                System.out.println(response.getBody().asString());
            }

            else
                log.error("All the Bills are not getting generated");

            int length = response.jsonPath().getList("billId").size();
            System.out.println(length);
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
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
                    .get("/bill/78")
                    .then()
                    .statusCode(200).body("billId",equalTo(78))
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("Getting Bill by id");
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }

            else
                log.error("The bill with the right id is not getting generated");

        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
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
                log.info("Getting the apid bill on the particular month");
                System.out.println(response.getBody().asString());
            }
            else
            {
                log.error("The Paid bill is not getting generated");
                System.out.println("Problem detected");
            }
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
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
                    .put("/bill/78")
                    .then()
                    .statusCode(200).body("billId",equalTo(78))
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("Put request for new request");
                Assert.assertEquals(response.statusCode(),200);
            }
            else
            {
                log.error("Put request for new request is not being generated");
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
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
                    .put("/bill/78")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("Sending mail to client");
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }

            else
                log.error("Mail is not sent to client");
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
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
                    .put("/bill/78")
                    .then()
                    .statusCode(200).body("billId",equalTo(78))
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("Changing the status");
                Assert.assertEquals(response.statusCode(),200);
            }
            else
            {
                log.error("The status is unable to change");
                System.out.println(response.getBody().asString());
            }
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
        }
    }


    @Test(priority = 14)
    public void Delete_Request(){
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    body(newrequest).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .delete("/bill/79")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("Deleting Request");
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }

            else
               log.error("The request is cannot be deleted");

        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
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
                    .post("/bill/3/2022/90")
                    .then()
                    .statusCode(200).body("billId",equalTo(90))
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("Post request successful");
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }

            else
                log.error("Post request not Successful");
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
        }
    }

    /** Negative test cases */


    @Test(priority = 16)
    public void Get_Bill_By_id_negative() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .get("/bill/69")
                    .then()
                    .statusCode(200).body("billId", equalTo(69))
                    .extract().response();
            if (response.statusCode() == 200) {
                Assert.assertEquals(response.statusCode(), 200);
                System.out.println(response.getBody().asString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(priority = 17)
    public void New_Request_negative() {
        try {
            Response response = given()
                    .baseUri(BaseUtilities.url).
                    body(newrequest).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .put("/bill/69")
                    .then()
                    .statusCode(200).body("billId", equalTo(69))
                    .extract().response();
            if (response.statusCode() == 200) {
                Assert.assertEquals(response.statusCode(), 200);
            } else {
                System.out.println(response.getBody().asString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}








