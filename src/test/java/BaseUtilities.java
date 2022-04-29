
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;





public class BaseUtilities {

    public static FileInputStream fileinput;
    public static FileOutputStream fileoutput;
    public static XSSFWorkbook workbook;
    public static XSSFSheet worksheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static String path_of_DB = "C:\\Users\\Database\\Credentials.xlsx";
    public static String sheet_name = "DB";
    public static String url = "https://acb-be-urtjok3rza-wl.a.run.app";

    public static String path_of_apiDatabase = "C:\\Users\\akashyab\\Downloads\\databaseAPI (1).xlsx";
    public static String sheetName_apiDB = "database_sheet";


    public static String test_get_token() throws IOException {
        Response response = given().
                baseUri(url).
                body(Create_user_json(getCellvalue(path_of_DB, sheet_name, 1, 0),
                        getCellvalue(path_of_DB, sheet_name, 1, 1)))
                .header("content-type", "application/json").
                when().
                post("/api/auth/signin").
                then().
                extract().response();
        JsonPath jsnPath = response.jsonPath();
        String final_res = jsnPath.get("accessToken");
        return final_res;
    }

    public static org.json.simple.JSONObject Create_user_json(String Name, String Pass) throws IOException {
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("username", Name);
        obj.put("password", Pass);
        return obj;
    }

    public static String getCellvalue(String FilePath, String SheetName, int rownum, int colnum) throws IOException {

        fileinput = new FileInputStream(FilePath);
        workbook = new XSSFWorkbook(fileinput);
        worksheet = workbook.getSheet(SheetName);
        row = worksheet.getRow(rownum);
        cell = row.getCell(colnum);
        DataFormatter formatter = new DataFormatter();
        String values = formatter.formatCellValue(cell);
        return values;
    }


    public static String getJSON(Map bodyParameters) {
        Gson gson = new Gson();
        String json = gson.toJson(bodyParameters, LinkedHashMap.class);
        return json;

    }
}
