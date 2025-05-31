package com.elektronicare.tests;

import com.elektronicare.config.AppiumConfig;
import com.elektronicare.utils.ExtentReportManager;
import com.elektronicare.utils.TestUtils;
import com.elektronicare.pages.OnboardingPage;
import com.elektronicare.pages.WelcomePage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Base test class with common setup and teardown methods
 */
public class BaseTest {
    
    protected AndroidDriver driver;
    
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("=== Starting ElektroniCare Test Suite ===");
        
        // Initialize ExtentReports
        ExtentReportManager.initReports();
        
        // Start Appium server
        AppiumConfig.startAppiumServer();
        
        System.out.println("Test suite setup completed");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Setting up test method...");
        
        // Initialize driver
        driver = AppiumConfig.initializeDriver();
        
        // Wait for app to launch and splash to complete
        TestUtils.waitFor(3000);
        
        System.out.println("Test method setup completed");
    }
    
    /**
     * Navigate through onboarding to welcome page
     * This should be called by tests that need to reach login/register pages
     */
    protected void navigateToWelcome() {
        OnboardingPage onboardingPage = new OnboardingPage();
        
        // Check if we're on onboarding page
        if (onboardingPage.isOnboardingPageDisplayed()) {
            logInfo("Navigating through onboarding to welcome page");
            onboardingPage.skipOnboarding();
            TestUtils.waitFor(2000);
        }
    }
    
    /**
     * Navigate to login page from welcome
     */
    protected void navigateToLogin() {
        navigateToWelcome();
        WelcomePage welcomePage = new WelcomePage();
        
        if (welcomePage.isWelcomePageDisplayed()) {
            logInfo("Navigating from welcome to login page");
            welcomePage.clickSignIn();
            TestUtils.waitFor(2000);
        }
    }
    
    /**
     * Navigate to register page from welcome
     */
    protected void navigateToRegister() {
        navigateToWelcome();
        WelcomePage welcomePage = new WelcomePage();
        
        if (welcomePage.isWelcomePageDisplayed()) {
            logInfo("Navigating from welcome to register page");
            welcomePage.clickCreateAccount();
            TestUtils.waitFor(2000);
        }
    }
    
    /**
     * Log info message
     */
    protected void logInfo(String message) {
        System.out.println("[INFO] " + message);
        ExtentReportManager.logInfo(message);
    }
    
    /**
     * Log pass message
     */
    protected void logPass(String message) {
        System.out.println("[PASS] " + message);
        ExtentReportManager.logPass(message);
    }
    
    /**
     * Log fail message
     */
    protected void logFail(String message) {
        System.out.println("[FAIL] " + message);
        ExtentReportManager.logFail(message);
    }
    
    /**
     * Log skip message
     */
    protected void logSkip(String message) {
        System.out.println("[SKIP] " + message);
        ExtentReportManager.logSkip(message);
    }
    
    /**
     * Create test in ExtentReport
     */
    protected void createTest(String testName, String description) {
        ExtentReportManager.createTest(testName, description);
    }
    
    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("Cleaning up test method...");
        
        // Handle test result for reporting
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test failed: " + result.getName());
            
            // Take screenshot on failure
            String screenshotPath = TestUtils.takeScreenshot(result.getName() + "_FAILED");
            if (screenshotPath != null) {
                ExtentReportManager.addScreenshot(screenshotPath);
            }
            
            ExtentReportManager.logFail("Test failed: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("Test passed: " + result.getName());
            ExtentReportManager.logPass("Test completed successfully");
        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("Test skipped: " + result.getName());
            ExtentReportManager.logSkip("Test was skipped: " + result.getThrowable().getMessage());
        }
        
        // Clean up ExtentReports thread local
        ExtentReportManager.removeTest();
        
        // Quit driver
        AppiumConfig.quitDriver();
        
        System.out.println("Test method cleanup completed");
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("=== Cleaning up Test Suite ===");
        
        // Flush ExtentReports
        ExtentReportManager.flushReports();
        
        // Stop Appium server
        AppiumConfig.stopAppiumServer();
        
        System.out.println("Test suite cleanup completed");
    }
    

    
    /**
     * Take screenshot and add to report
     */
    protected void takeScreenshot(String description) {
        String screenshotPath = TestUtils.takeScreenshot(description);
        if (screenshotPath != null) {
            ExtentReportManager.addScreenshot(screenshotPath);
            logInfo("Screenshot captured: " + description);
        }
    }
    
    /**
     * Wait for specified duration
     */
    protected void waitFor(long milliseconds) {
        TestUtils.waitFor(milliseconds);
    }
    
    /**
     * Get current driver instance
     */
    protected AndroidDriver getDriver() {
        return AppiumConfig.getDriver();
    }
}