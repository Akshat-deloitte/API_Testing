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

public class BaseUtilities {

    public static FileInputStream fileinput;
    public static XSSFWorkbook workbook;
    public static XSSFSheet worksheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static String path_of_DB = "C:\\Users\\Database\\Credentials.xlsx";
    public static String sheet_name = "DB";
    public static String url = "https://acb-be-urtjok3rza-wl.a.run.app";
    private static FileOutputStream fileoutput;


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

    public static void Write_data_id(String id, int i,int j) {
        String Excel_file_path = ("C:\\Users\\akashyab\\Desktop\\ProjectData.xlsx");
        try {
            FileInputStream fis = new FileInputStream(Excel_file_path);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.getRow(i);

            System.out.println("Writing in Excel");
            cell = row.createCell(j);
            cell.setCellValue(id);
            fis.close();
            FileOutputStream fos = new FileOutputStream(Excel_file_path);
            workbook.write(fos);
            //fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFILE(String FilePath,String SheetName,int rownum,int colnum,String data) throws IOException
    {
        fileinput=new FileInputStream(FilePath);
        workbook=new XSSFWorkbook(fileinput);
        worksheet=workbook.getSheet(SheetName);
        row=worksheet.getRow(rownum);
        cell=row.createCell(colnum);
        cell.setCellValue(data);
        fileoutput = new FileOutputStream(FilePath);
        workbook.write(fileoutput);
        workbook.close();
        fileinput.close();
        fileoutput.close();
    }
}
