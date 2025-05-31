package com.elektronicare.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ExtentReports manager for test reporting
 */
public class ExtentReportManager {
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    /**
     * Initialize ExtentReports
     */
    public static void initReports() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String reportPath = System.getProperty("user.dir") + "/test-reports/ElektroniCare_Test_Report_" + timestamp + ".html";
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("ElektroniCare Automation Test Report");
            sparkReporter.config().setReportName("Mobile App Test Results");
            sparkReporter.config().setTheme(Theme.STANDARD);
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // System information
            extent.setSystemInfo("Application", "ElektroniCare Beta 1");
            extent.setSystemInfo("Platform", "Android");
            extent.setSystemInfo("Automation Tool", "Appium");
            extent.setSystemInfo("Framework", "TestNG");
            extent.setSystemInfo("Tester", "Automation Team");
            
            System.out.println("ExtentReports initialized: " + reportPath);
        }
    }
    
    /**
     * Create a new test in the report
     */
    public static void createTest(String testName, String description) {
        if (extent != null) {
            ExtentTest extentTest = extent.createTest(testName, description);
            test.set(extentTest);
        }
    }
    
    /**
     * Get current test instance
     */
    public static ExtentTest getTest() {
        return test.get();
    }
    
    /**
     * Log info message
     */
    public static void logInfo(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            currentTest.info(message);
        }
    }
    
    /**
     * Log pass message
     */
    public static void logPass(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            currentTest.pass(message);
        }
    }
    
    /**
     * Log fail message
     */
    public static void logFail(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            currentTest.fail(message);
        }
    }
    
    /**
     * Log skip message
     */
    public static void logSkip(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            currentTest.skip(message);
        }
    }
    
    /**
     * Add screenshot to report
     */
    public static void addScreenshot(String screenshotPath) {
        if (screenshotPath != null) {
            ExtentTest currentTest = getTest();
            if (currentTest != null) {
                currentTest.addScreenCaptureFromPath(screenshotPath);
            }
        }
    }
    
    /**
     * Flush reports
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
    
    /**
     * Clean up thread local
     */
    public static void removeTest() {
        test.remove();
    }
}