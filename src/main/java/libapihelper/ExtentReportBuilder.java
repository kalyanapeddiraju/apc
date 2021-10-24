package libapihelper;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class ExtentReportBuilder{

    public static ExtentTest getExtentTest() {
        return extentTest;
    }
    public static void setExtentTest(ExtentTest extentTest) {
        ExtentReportBuilder.extentTest = extentTest;
    }
    static ExtentTest extentTest;

    public static ExtentTest getExtentTestLog() {
        return extentTestLog;
    }

    public static void setExtentTestLog(ExtentTest extentTestLog) {
        ExtentReportBuilder.extentTestLog = extentTestLog;
    }

    static ExtentTest extentTestLog;
    static ExtentReports er;
    static String ExtentRepath;
    public static void intilizeExtentreport(String message, String description,String fileName) {
        ExtentRepath = System.getProperty("user.dir") + "/target/"+ fileName+".html";
        er = new ExtentReports(ExtentRepath);
        extentTest = er.startTest(message, description);
        extentTestLog =extentTest;
    }
    public static void logExtentReport(String inputRequest,
                                         String actualResponse,
                                         String exceptedResponse,
                                         String actualHttpStatus,
                                         String exceptedStatus,
                                       String testCaseDescription) {
        extentTest.log(LogStatus.INFO, " Test Case Started", testCaseDescription);
        extentTest.log(LogStatus.INFO, "Input Request", inputRequest);
        extentTest.log(LogStatus.INFO, "Excepted Response", exceptedResponse);
        extentTest.log(LogStatus.INFO, "Actual Response", actualResponse);
        extentTest.log(LogStatus.INFO, "Excepted HTTP Status", exceptedStatus);
        extentTest.log(LogStatus.INFO, "Actual HTTP Status", String.valueOf(actualHttpStatus));
        extentTest.log(LogStatus.INFO, " Test Case Ended");
        er.endTest(extentTest);
    }
    public static void onFinish() {
        er.flush();
        er.close();

    }



}


