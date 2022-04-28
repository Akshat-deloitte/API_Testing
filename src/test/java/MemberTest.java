import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class MemberTest {


    private  static final String LOG_FILE = "log4j.properties";
    private static Logger log  = LogManager.getLogger(MemberTest.class);

    public static String token;
    static String member_id;
    public static Map<String, String> List_of_Users = new LinkedHashMap<>();


    static String Name_toBeUpdated;

    static {
        try {
            Name_toBeUpdated = BaseUtilities.getCellvalue(BaseUtilities.path_of_apiDatabase, BaseUtilities.sheetName_apiDB, 3, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String id_ofMember;
    static String name_ofMember;

    static {
        try {
            id_ofMember = BaseUtilities.getCellvalue(BaseUtilities.path_of_apiDatabase, BaseUtilities.sheetName_apiDB, 5, 1);
            name_ofMember = BaseUtilities.getCellvalue(BaseUtilities.path_of_apiDatabase, BaseUtilities.sheetName_apiDB, 5, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static {
        try {
            member_id = BaseUtilities.getCellvalue(BaseUtilities.path_of_apiDatabase, BaseUtilities.sheetName_apiDB, 1, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String idTo_beDeleted;


    static {
        try {
            idTo_beDeleted = BaseUtilities.getCellvalue(BaseUtilities.path_of_apiDatabase, BaseUtilities.sheetName_apiDB, 15, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void get_availableUserList() {
        /*
        *This Function will update the available users list
        * everytime when it is called
         */
            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type", "application/json")
                    .when()
                    .get("/member")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.getBody().asString());

            int size = response.jsonPath().getList("id").size();



        JsonPath jsnPath = new JsonPath(response.asString());

            for (int i = 1; i <= size; i++) {
                String  id = jsnPath.getString("[" + i + "]" + ".id");
                String name = jsnPath.getString("[" + i + "]" + ".name");

                List_of_Users.put(id,name);
            }

        System.out.println("Numbers of users are" + List_of_Users);
        System.out.println("Left user after deletions are" + List_of_Users.size());

    }




    @BeforeTest
    public void Get_Bearer_Token() throws IOException {
        token = BaseUtilities.test_get_token();
    }

    @Test(priority = 1)
    public static void validating_All_Member()
    {
        log.info("validating_All_Member");
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .get("/member")
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.getBody().asString());

            int size = response.jsonPath().getList("id").size();
            JsonPath jsnPath = response.jsonPath();

                for(int i=1;i<=size;i++) {
                String id =  jsnPath.get("[" + i + "]" + ".id").toString();
                String name = jsnPath.get("[" + i + "]" + ".name").toString();

                System.out.println(id);
                System.out.println(name);

                List_of_Users.put(id,name);


            }
            if(response.getBody().asString().length() !=0)
            {
                System.out.println(   size +  "  are available in the list" );
                Assert.assertTrue(true);
            }else
            {
                Assert.assertTrue(false);
            }
        }catch (Exception e){
            log.error("Can't Validate All Member, Error Occured");
            System.out.println(e);
        }}




     @Test(priority = 2)
    public static void validating_Member_by_id()
    {
        log.info("validating_Member_by_id");
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .get("/member/" + member_id)
                    .then()
                    .statusCode(200)
                    .extract().response();
            System.out.println(response.getBody().asString());

            JsonPath jsnPath = response.jsonPath();
            String final_res = jsnPath.get("name");
           System.out.println(final_res);
            System.out.println(List_of_Users);



            if(BaseUtilities.getCellvalue(BaseUtilities.path_of_apiDatabase,BaseUtilities.sheetName_apiDB,1,0).equals(final_res))
            {
                System.out.println(final_res  + "   Name Matches with the id present in the database");
                Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false);
                System.out.println("Name do not Matches with the id present in the database");
            }

        }catch (Exception e){
            log.error("Can't Validate Member_by id, some error Occurred");
            System.out.println(e);
        }
    }


    @Test(priority = 3)
    public static void validating_UpdateMember()
    {
       log.info("validating_UpdateMember");
        Map bodyParameters = new LinkedHashMap();
            bodyParameters.put("name",Name_toBeUpdated);

        Gson gson = new Gson();
        String json = gson.toJson(bodyParameters, LinkedHashMap.class);


        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .body(json).
                    when()
                    .put("/member/" + id_ofMember)
                    .then()
                    .statusCode(200)
                    .extract().response();


            JsonPath jsnPath = response.jsonPath();
            String name_inResponse = jsnPath.get("name");

            System.out.println("Initial name" + name_ofMember);
            System.out.println("After update name is " + name_inResponse);

            System.out.println("User Data " + response.getBody().asString());

            if(name_inResponse.equals(Name_toBeUpdated))
            {
                System.out.println("Data updation can be done");
                Assert.assertTrue(true);
            }

        }catch (Exception e){
            log.error("Can't Validate Validating Update_Member,Some error Occured");
            System.out.println(e);
        }


    }




    @Test(priority = 4)
    public static void validating_DeleteMember()
    {
        log.info("validating_DeleteMember");
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                            .
                    when()
                    .delete("/member/"+ idTo_beDeleted )
                    .then()
                    .statusCode(200)
                    .extract().response();

           get_availableUserList();

            boolean isKeyPresent = List_of_Users.containsKey(idTo_beDeleted);

            if(List_of_Users.get(idTo_beDeleted)==null)
            {

                Assert.assertTrue(true);
            }
        }catch (Exception e){
            System.out.println(e);
            log.error("Can't Validate Delete Member, Some Error Occured");
        }


    }





    @Test(priority = 5)
    public static void validating_addProjects()
    {
        log.info("validating_addProjects");

        Map bodyParameters = new LinkedHashMap();
        bodyParameters.put("project","123");
         String json = BaseUtilities.getJSON(bodyParameters);

        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .body(json).
                    when()
                    .put("/member/addProjects/" + id_ofMember)
                    .then()
                    .extract().response();


            System.out.println(response.getBody().asString());
            JsonPath jsnPath = new JsonPath(response.asString());

            String message =  jsnPath.getString("error");
            System.out.println("required Status code = 200 but got  " + response.getStatusCode()  +  message);

            String statusCode = String.valueOf(response.getStatusCode());

            if(!statusCode.equals("200"))
            {
                Assert.assertTrue(false);
            }


        }catch (Exception e){
            log.error("Can't Validate addProjects,Some error Occured");
            System.out.println(e);
        }


    }

   /*
   *Negative Test Cases Below
   */

    @Test(priority = 100)
    public static void validatinG_Member_by_invalid_id()
    {
        log.info("validating_All_Member with invalid id");
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .get("/member/" + "1a")
                    .then()
                    .extract().response();
            System.out.println(response.getBody().asString());

            JsonPath jsnPath = response.jsonPath();
            String final_res = jsnPath.get("name");


            String statusCode = String.valueOf(response.getStatusCode());
            System.out.println(statusCode);
            if(statusCode.equals("400"))
            {
                System.out.println(final_res  + " Able to detect invalid id (please type valid id)");
                Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false);
                System.out.println("Unable to detect invalid id");
            }

        }catch (Exception e){
            log.error("Can't Validate Member_by id, some error Occurred");
            System.out.println(e);
        }
    }


    @Test(priority = 101)
    public static void validating_updateMember_by_wrongData()
    {
        log.info("validating_UpdateMember");
        Map bodyParameters = new LinkedHashMap();
        bodyParameters.put("name",Name_toBeUpdated+";");

        Gson gson = new Gson();
        String json = gson.toJson(bodyParameters, LinkedHashMap.class);


        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .body(json).
                    when()
                    .put("/member/" + id_ofMember)
                    .then()
                    .extract().response();


            JsonPath jsnPath = response.jsonPath();
            String name_inResponse = jsnPath.get("name");

            System.out.println("Initial name" + name_ofMember);
            System.out.println("After update name is " + name_inResponse);

            System.out.println("Response " + response.getBody().asString());

            String statusCode = String.valueOf(response.getStatusCode());
            System.out.println(statusCode);

            if(statusCode.equals("400"))
            {
                System.out.println("Invalid data Can be detected (PLease send valid data for updation)");
                Assert.assertTrue(true);
            }

        }catch (Exception e){
            log.error("Unable to detect Invalid data");
            System.out.println(e);
        }


    }




    @Test(priority = 102)
    public static void validating_DeleteMember_wrongID()
    {
        log.info("validating_DeleteMember");
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                            .
                    when()
                    .delete("/member/"+ "shfiwi" )
                    .then()
                    .extract().response();



            String statusCode = String.valueOf(response.getStatusCode());
            System.out.println(statusCode);

            if(statusCode.equals("400"))
            {
                System.out.println("Application is able to detect invalid id (PLease type valid id to delete the member)");
                Assert.assertTrue(true);
            }
            else
            {
                System.out.println("Application is Unable to detect invalid id");
                Assert.assertTrue(false);
            }
        }catch (Exception e){
            System.out.println(e);
            log.error("Can't Validate Delete Member, Some Error Occured");
        }


    }


    @Test(priority = 103)
    public static void validating_updateMember_by_emotyData()
    {
        log.info("validating_UpdateMember BY PASSING nothing");
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .body("").
                    when()
                    .put("/member/" + id_ofMember)
                    .then()
                    .extract().response();


            JsonPath jsnPath = response.jsonPath();
            String name_inResponse = jsnPath.get("name");

            System.out.println("Initial name" + name_ofMember);
            System.out.println("After update name is " + name_inResponse);

            System.out.println("Response " + response.getBody().asString());

            String statusCode = String.valueOf(response.getStatusCode());
            System.out.println(statusCode);

            if(statusCode.equals("400"))
            {
                System.out.println("Invalid data Can be detected (PLease send valid data for updation)");
                Assert.assertTrue(true);
            }

        }catch (Exception e){
            log.error("Unable to detect Invalid data");
            System.out.println(e);
        }


    }

    @Test(priority = 100)
    public static void validatinG_Member_by__id()
    {
        log.info("validating_All_Member by passing name instead of i id");
        try{

            Response response = given()
                    .baseUri(BaseUtilities.url).
                    header("Authorization",
                            "Bearer " + token).header("content-type","application/json")
                    .when()
                    .get("/member/" + "AKSHAT")
                    .then()
                    .extract().response();
            System.out.println(response.getBody().asString());

            JsonPath jsnPath = response.jsonPath();
            String final_res = jsnPath.get("name");


            String statusCode = String.valueOf(response.getStatusCode());
            System.out.println(statusCode);
            if(statusCode.equals("400"))
            {
                System.out.println(final_res  + " Able to detect invalid id (please type valid id)");
                Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false);
                System.out.println("Unable to detect invalid id");
            }

        }catch (Exception e){
            log.error("Can't Validate Member_by id, some error Occurred");
            System.out.println(e);
        }
    }





}

