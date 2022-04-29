import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;


public class LoginTest {


    private  static final String LOG_FILE = "log4j.properties";
    private static Logger log  = LogManager.getLogger(LoginTest.class);

    public String token = "";
    public  String path_of_file = "C:\\Users\\adityakumar3\\Desktop\\Learnings\\databaseAPI.xlsx";
    public  String sheet_Name = "Database";
    public String title = BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,0);
    public String description = BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,1);
    public String teamLeadId = BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,2);
    public String clientId = BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,3);
    public String projectStatus = BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,4);
    public String createdBy= BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,5);
    public String createdOn = BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,6);
    public String completionDate= BaseUtilities.getCellvalue(path_of_file,sheet_Name,1,7);
    public String new_des = BaseUtilities.getCellvalue(path_of_file,sheet_Name,2,1);
    public boolean condition = false;

    public LoginTest() throws IOException {
    }


    @BeforeTest
    public void Get_Bearer_Token() throws IOException {
        token = BaseUtilities.test_get_token();
        System.out.println(token);
    }


    @Test(priority = 6)
    public void Get_All_Projects() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .get("/project")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.getBody().asString());

            int size = response.jsonPath().getList("id").size();
            System.out.println(size);
            if(size == 19){
                log.info("Response recieved");
                Assert.assertTrue(true);
            }
            else {
                log.error("Response does not contain all projects");
                Assert.assertTrue(false);
            }


        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    @Test(priority = 7)
    public void Get_All_Project_ByMonth() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .get("/projects/6/2023")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.getBody().asString());

            int size = response.jsonPath().getList("id").size();
            System.out.println(size);
            if(size == 9){
                log.info("Response recieved");
                Assert.assertTrue(true);
            }
            else {
                log.error("Response does not contain all projects");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    @Test(priority = 8)
    public void Add_Project() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .body(post_Body())
                    .when()
                    .post("/project")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.getBody().asString());


           if(get_Int_data_FromJson(response,"projectId") != 0){
                 log.info("Project created");
                 System.out.println("Project created with ProjectId "+get_Int_data_FromJson(response,"projectId"));
                 Assert.assertTrue(true);
           }

           else {
               log.error("Unable to create Project");
               Assert.assertTrue(false);
           }


        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    @Test(priority = 9)
    public void Get_Project_ById() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .get("/project/98")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.getBody().asString());


            if((get_Int_data_FromJson(response,"projectId") == 98) ){
                 log.info("Response recieved");
                 Assert.assertTrue(true);
           }

           else {
                log.error("Project Not found");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    @Test(priority = 10)
    public void Update_Project() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .body(put_Body())
                    .when()
                    .put("/project/107")
                    .then()
                    .statusCode(200)
                    .extract().response();

            System.out.println(response.getBody().asString());

            if( (get_String_data_FromJson(response,"description")).equals(new_des)) {
                 log.info("Description updated");
                 Assert.assertTrue(true);
           }

           else {
                log.error("Unable to update description");
                Assert.assertTrue(false, "Unable to update Description ");
            }
        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    @Test(priority = 11)
    public void Delete_Project() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .delete("/project/72")
                    .then()
                    .extract().response();

            System.out.println(response.getBody().asString());
            validate_Delete("72");

        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }



    @Test(priority = 12)
    public void Add_Members_ToProject() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .body(invalid_Body())
                    .when()
                    .put("/project/addMembers/107")
                    .then()
                    .extract().response();

            if(response.statusCode() == 400 ){
                log.error("Failed due to Invalid body");
                System.out.println("Status code:" +String.valueOf(response.statusCode()));
                System.out.println(get_String_data_FromJson(response,"error"));
                Assert.assertTrue(false);
            }

            else
                Assert.assertTrue(true);

        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    /**   Negative test cases   */

    @Test(priority = 8)
    public void Negative_01() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .get("/project/84")
                    .then()
                    .extract().response();
            System.out.println(response.getBody().asString());


            if(response.statusCode() == 500){
                log.error("Internal server error , Invalid URL ");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    @Test(priority = 9)
    public void Negative_02() {
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .body(invalid_Body())
                    .when()
                    .put("/project/107")
                    .then()
                    .statusCode(200)
                    .extract().response();

            System.out.println(response.getBody().asString());

            if(response.statusCode() == 400){
                log.error("Bad request , Invalid body ");
                Assert.assertTrue(false);
            }
        } catch (Exception e) {
            log.error("Error message "+e.getMessage());
        }
    }


    public org.json.simple.JSONObject post_Body(){
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("title",title);
        obj.put("description",description);
        obj.put("teamLeadId",Integer.parseInt(teamLeadId));
        obj.put("clientId",Integer.parseInt(clientId));
        obj.put("projectStatus",projectStatus);
        obj.put("createdBy",Integer.parseInt(createdBy));
        obj.put("createdOn",createdOn);
        obj.put("completionDate",completionDate);
        return obj;
    }





    public org.json.simple.JSONObject put_Body() throws IOException {
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("description",new_des);
        return obj;
    }

    public org.json.simple.JSONObject invalid_Body() throws IOException {
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("Member","Aditya");
        return obj;
    }



    public static int  get_Int_data_FromJson(Response response , String attribute){
        JsonPath jsnPath = response.jsonPath();
        int final_res = jsnPath.get(attribute);
        return final_res;
    }

    public static String  get_String_data_FromJson(Response response , String attribute){
        JsonPath jsnPath = response.jsonPath();
        String final_res = jsnPath.get(attribute);
        return final_res;
    }


    public void validate_Delete(String ID){
        try {

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .get("/project/"+ID)
                    .then()
                    .extract().response();

            if((get_Int_data_FromJson(response,"status") == 500) ){
                log.info("Project Deleted");
                Assert.assertTrue(true);
            }

            else {
                log.error("Project Not deleted ");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

















