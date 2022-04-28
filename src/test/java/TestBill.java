import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import static io.restassured.RestAssured.given;

public class TestBill extends BaseUtilities {
    public String t = "";
    File new_request = new File("C:\\Users\\ananshankar\\Documents\\ProductWeek\\API\\API_Testing\\new_request.json");

    /**
     * t -> variable to store the token data
     * new_request -> fetches the new_request json file from the system
     */

    @BeforeTest
    public void get_token() throws IOException {
        t = BaseUtilities.test_get_token();
    }

    @Test(priority = 1)
    public void send_mail_to_team_lead() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .body(new_request)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
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
            else
                System.out.println("There is an error in sending mail to team lead...!!!");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(priority = 2)
    public void get_verified_bills(){
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("/bill/verified/3/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null)
                System.out.println(response.getBody().asString());
            else
                System.out.println("There is an error in verified_bill...!!!");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 3)
    public void get_unverified_bills(){
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("/bill/unverified/2/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null)
                System.out.println(response.getBody().asString());
            else
                System.out.println("There is an error in unverified_bill...!!!");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 4)
    public void unpaid_bills() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .get("bill/unpaid/2/2022")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.getBody().asString() != null)
                System.out.println(response.getBody().asString());
            else
                System.out.println("There is an error in unpaid_bill...!!!");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Test(priority = 5)
    public void change_client_status() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .body(new_request)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .put("/clientStatus/77/newGenerated")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }
            else
                System.out.println("There is an error in changing client status...!!!");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(priority = 6)
    public void change_team_lead_status() {
        try{
            Response response = given()
                    .baseUri(BaseUtilities.url)
                    .body(new_request)
                    .header("Authorization", "Bearer " + t)
                    .header("content-type","application/json")
                    .when()
                    .put("/teamStatus/65/newGenerated")
                    .then()
                    .statusCode(200)
                    .extract().response();
            if (response.statusCode() == 200)
            {
                Assert.assertEquals(response.statusCode(),200);
                System.out.println(response.getBody().asString());
            }
            else
                System.out.println("There is an error in changing team lead status...!!!");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(priority = 7)
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
            if (response.getBody().asString() != null)
                System.out.println(response.getBody().asString());
            else
                System.out.println("There is an error in total money paid...!!!");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }


}
