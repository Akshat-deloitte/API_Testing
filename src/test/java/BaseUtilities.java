import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
//import test.BaseClass;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import static config.TestUserData.LoginUserDataJson;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.config;
//import static org.apache.commons.codec.digest.UnixCrypt.body;
public class BaseUtilities {

    public static FileInputStream fileinput;
    public static XSSFWorkbook workbook;
    public static XSSFSheet worksheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static String path_of_DB = "C:\\Users\\agajendra\\Desktop\\Api_testing\\API_Testing\\Credentials.xlsx";
    public static String sheet_name = "DB";
    public static String url = "https://acb-be-urtjok3rza-wl.a.run.app";
    public static String test_get_token() throws IOException {
        Response response = given().
                baseUri(url).
                body(Create_user_json(getCellvalue(path_of_DB,sheet_name,1,0),
                        getCellvalue(path_of_DB,sheet_name,1,1)))
                .header("content-type","application/json").
                when().
                post("/api/auth/signin").
                then().
                extract().response();
        JsonPath jsnPath = response.jsonPath();
        String final_res = jsnPath.get("accessToken");
        return final_res;
    }

    public  static  org.json.simple.JSONObject Create_user_json(String Name,String Pass) throws IOException {
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("username",Name);
        obj.put("password",Pass);
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

//    public static void Write_data_id(String id, int i,int j) {
//        String Excel_file_path = ("C:\\Users\\akashyab\\Downloads\\ToDo_Cred.xlsx");
//        try {
//            FileInputStream fis = new FileInputStream(Excel_file_path);
//            XSSFWorkbook workbook = new XSSFWorkbook(fis);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            XSSFRow row = null;
//            XSSFCell cell = null;
//            row = sheet.getRow(i);
//
//            System.out.println("Writing in Excel");
//            cell = row.createCell(j);
//            cell.setCellValue(id);
//            fis.close();
//            FileOutputStream fos = new FileOutputStream(Excel_file_path);
//            workbook.write(fos);
//            //fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }






}