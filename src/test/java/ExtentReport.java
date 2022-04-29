import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

    public static ExtentReports ReportGenerator() {
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("C:\\Users\\akashyab\\Desktop\\APITesting_ClientBilling\\API_Testing\\ExtentReports\\Extreport.html");
        extentReports.attachReporter(reporter);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("Client Billing API Testing");
        reporter.config().setReportName("Client Billing API Testing");
        return extentReports;
    }
}
