import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import static io.restassured.RestAssured.given;

public class TestBill extends BaseUtilities {
    public String t = "";
    File new_request = new File("C:\\Users\\akashyab\\Desktop\\APITesting_ClientBilling\\API_Testing\\new_request.json");
    private  static final String LOG_FILE = "log4j.properties";
    private static Logger log  = LogManager.getLogger(LoginTest.class);

    /**
     * t -> variable to store the token data
     * new_request -> fetches the new_request json file from the system
     */

    @BeforeTest
    public void get_token() throws IOException {
        t = BaseUtilities.test_get_token();
    }

    @Test(priority = 16)
    public void send_mail_to_team_lead() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .body(new_request)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .put("/bill/78")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("sending mail to team lead");
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }
            else
                log.error("Cannot send mail to team lead.!");
        }
        catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }

    @Test(priority = 17)
    public void get_verified_bills(){
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("/bill/verified/4/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null)
                log.info("retrieving verified bills");
            else
                log.error("Cannot view verified bills.!");
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
        }
    }

    @Test(priority = 18)
    public void get_unverified_bills(){
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("/bill/unverified/4/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null)
                log.info("retrieving unverified bills");
            else
                log.error("Cannot view unverified bills.!");
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
        }
    }

    @Test(priority = 19)
    public void unpaid_bills() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("bill/unpaid/4/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null)
                log.info("retrieving unpaid bills");
            else
                log.error("Cannot view unpaid bills.!");
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
        }
    }

    @Test(priority = 20)
    public void change_client_status() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .body(new_request)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .put("/clientStatus/78/newGenerated")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("changing client progress status");
                Assert.assertEquals(response.statusCode(),200);
            }
            else
                log.error("Cannot update client progress status");
        }
        catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }

    @Test(priority = 21)
    public void change_team_lead_status() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .body(new_request)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .put("/teamStatus/78/newGenerated")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                log.info("changing team lead progress status");
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }
            else
                log.error("Cannot update team lead progress status");
        }
        catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }

    @Test(priority = 22)
    public void get_paid_money() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("/paidMoney/2/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null) {
                log.info("retieving total paid money");
                System.out.println(response.getBody().asString());
            }
            else
                log.error("Cannot view total money paid.!");
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
        }
    }


    /** Negative test case */


    @Test(priority = 8)
    public void get_paid_money_n() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("/paidMoney/4/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200){
                log.info("Invalid test case.!");
                Assert.assertEquals(response.statusCode(),200);
            }
            else
                log.error("There is an error in total money paid...!!!");
        }
        catch (Exception e)
        {
            log.error("Error message "+e.getMessage());
        }
    }
}
