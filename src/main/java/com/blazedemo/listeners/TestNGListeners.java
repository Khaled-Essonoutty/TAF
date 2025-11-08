package com.blazedemo.listeners;

import com.blazedemo.drivers.UITest;
import com.blazedemo.drivers.WebDriverProvider;
import com.blazedemo.utils.FileUtils;
import com.blazedemo.utils.dataReader.PropertiesFileReader;
import com.blazedemo.utils.logs.LogsUtil;
import com.blazedemo.utils.media.ScreenRecordUtils;
import com.blazedemo.utils.media.ScreenshotUtil;
import com.blazedemo.utils.report.AllureAttachmentManager;
import com.blazedemo.utils.report.AllureConstants;
import com.blazedemo.utils.report.AllureEnvironmentManager;
import com.blazedemo.utils.report.AllureReportGenerator;
import com.blazedemo.utils.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener {


    public void onExecutionStart() {
        LogsUtil.info("Test Execution Started.");
        cleanOutputDirectories();
        LogsUtil.info("Old Test-Output directory is cleaned.");
        createTestOutputDirectories();
        LogsUtil.info("New Test-Output directory is created.");
        PropertiesFileReader.loadProperties();
        LogsUtil.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsUtil.info("Allure environment set");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsUtil.info("Test Execution Finished.");
    }


    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod())
        {
            ScreenRecordUtils.startRecording();
            LogsUtil.info("Test", testResult.getName(), "Started.");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if(method.isTestMethod()){
            if(testResult.getInstance() instanceof UITest)
            {
                ScreenRecordUtils.stopRecording(testResult.getName());
                /*
                 *testResult.getInstance(): returns the actual test class instance (the object of the test class that was running).
                 * instanceof WebDriverProvider: Is this test instance an object of a class that implements or extends WebDriverProvider?
                 * If yes, then it also casts it automatically to a variable named provider.
                 *
                 * Summary: - Check if the current test class implements the WebDriverProvider interface.
                 *          - If it does, then create a local variable provider referencing it.
                 *  */
                if(testResult.getInstance() instanceof WebDriverProvider provider)
                    driver = provider.getDriver();
                switch (testResult.getStatus())
                {
                    case ITestResult.SUCCESS -> ScreenshotUtil.takeScreenshot(driver, "passed-" + testResult.getName());
                    case ITestResult.FAILURE -> ScreenshotUtil.takeScreenshot(driver, "failed-" + testResult.getName());
                    case ITestResult.SKIP -> ScreenshotUtil.takeScreenshot(driver, "skipped-" + testResult.getName());
                }
                AllureAttachmentManager.attachRecords(testResult.getName());
            }

            LogsUtil.info("Test", testResult.getName(), "Finished.");
            Validation.assertAll(testResult);
            AllureAttachmentManager.attachLogs();
        }
    }


    public void onTestSuccess(ITestResult result) {
        LogsUtil.info("Test", result.getName(), "Passed.");
    }

    public void onTestFailure(ITestResult result) {
        LogsUtil.info("Test", result.getName(), "Failed.");
    }

    public void onTestSkipped(ITestResult result) {
        LogsUtil.info("Test", result.getName(), "Skipped.");
    }

    private void cleanOutputDirectories(){
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotUtil.screenshotPath));
        FileUtils.cleanDirectory(new File(ScreenRecordUtils.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));
        FileUtils.forceDelete(new File(LogsUtil.LOGS_PATH +"logs.log"));
        FileUtils.forceDelete(new File("src/test/resources/downloads/invoice.txt"));
    }

    private void createTestOutputDirectories()
    {
        com.blazedemo.utils.FileUtils.createDirectory(ScreenshotUtil.screenshotPath);
        com.blazedemo.utils.FileUtils.createDirectory(ScreenRecordUtils.RECORDINGS_PATH);
        com.blazedemo.utils.FileUtils.createDirectory("src/test/resources/downloads/");
    }
}
