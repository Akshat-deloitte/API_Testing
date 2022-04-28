import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.config.LogConfig;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {


    public static ExtentReports extent;
    public static ExtentTest test;


    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest("" + result.getName());
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("the test case is passed ");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test Failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
        extent = ExtentReport.ReportGenerator();

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
















