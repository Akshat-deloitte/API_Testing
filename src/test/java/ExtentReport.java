import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

    public static ExtentReports ReportGenerator() {
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("C:\\Users\\adityakumar3\\APItesting_MainAssignment\\ExtentReport\\Extreport.html");
        extentReports.attachReporter(reporter);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("XYZ_Bank");
        reporter.config().setReportName("XYZ_Extent_report");
        return extentReports;

    }

}
