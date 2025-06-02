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

        try {
            // Initialize driver
            driver = AppiumConfig.initializeDriver();

            if (driver == null) {
                throw new RuntimeException("Failed to initialize driver - driver is null");
            }

            System.out.println("Driver initialized successfully: " + driver.getSessionId());

            // Wait for app to launch and splash to complete
            TestUtils.waitFor(4000);

            System.out.println("Test method setup completed");

        } catch (Exception e) {
            System.err.println("Failed to setup test method: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Test method setup failed", e);
        }
    }

    /**
     * Initialize driver if not already initialized
     */
    protected void initializeDriver() {
        try {
            if (driver == null) {
                System.out.println("Driver is null, initializing...");
                driver = AppiumConfig.initializeDriver();

                if (driver == null) {
                    throw new RuntimeException("Failed to initialize driver");
                }

                System.out.println("Driver initialized with session: " + driver.getSessionId());
                TestUtils.waitFor(2000); // Wait for app to stabilize
            } else {
                // Check if existing driver is still active
                try {
                    String currentActivity = driver.currentActivity();
                    System.out.println("Driver is active, current activity: " + currentActivity);
                } catch (Exception e) {
                    System.out.println("Existing driver seems inactive, reinitializing...");
                    driver = AppiumConfig.initializeDriver();
                    if (driver == null) {
                        throw new RuntimeException("Failed to reinitialize driver");
                    }
                    TestUtils.waitFor(2000);
                }
            }
        } catch (Exception e) {
            System.err.println("Driver initialization error: " + e.getMessage());
            throw new RuntimeException("Cannot initialize driver", e);
        }
    }

    /**
     * Check if driver is available and active
     */
    protected boolean isDriverActive() {
        try {
            if (driver == null) {
                return false;
            }
            // Try to get current activity to check if driver is responsive
            driver.currentActivity();
            return true;
        } catch (Exception e) {
            System.out.println("Driver check failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigate through onboarding to welcome page
     * This should be called by tests that need to reach login/register pages
     */
    protected void navigateToWelcome() {
        try {
            // Ensure driver is active before navigation
            if (!isDriverActive()) {
                initializeDriver();
            }

            OnboardingPage onboardingPage = new OnboardingPage();

            // Check if we're on onboarding page
            if (onboardingPage.isOnboardingPageDisplayed()) {
                logInfo("Navigating through onboarding to welcome page");
                onboardingPage.skipOnboarding();
                TestUtils.waitFor(3000);
            }
        } catch (Exception e) {
            System.err.println("Navigation to welcome failed: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to welcome page", e);
        }
    }

    /**
     * Navigate to login page from welcome
     */
    protected void navigateToLogin() {
        try {
            navigateToWelcome();

            if (!isDriverActive()) {
                throw new RuntimeException("Driver not active before navigating to login");
            }

            WelcomePage welcomePage = new WelcomePage();

            if (welcomePage.isWelcomePageDisplayed()) {
                logInfo("Navigating from welcome to login page");
                welcomePage.clickSignIn();
                TestUtils.waitFor(2000);
            }
        } catch (Exception e) {
            System.err.println("Navigation to login failed: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to login page", e);
        }
    }

    /**
     * Navigate to register page from welcome
     */
    protected void navigateToRegister() {
        try {
            navigateToWelcome();

            if (!isDriverActive()) {
                throw new RuntimeException("Driver not active before navigating to register");
            }

            WelcomePage welcomePage = new WelcomePage();

            if (welcomePage.isWelcomePageDisplayed()) {
                logInfo("Navigating from welcome to register page");
                welcomePage.clickCreateAccount();
                TestUtils.waitFor(2000);
            }
        } catch (Exception e) {
            System.err.println("Navigation to register failed: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to register page", e);
        }
    }

    /**
     * Log info message
     */
    protected void logInfo(String message) {
        System.out.println("[INFO] " + message);
        try {
            ExtentReportManager.logInfo(message);
        } catch (Exception e) {
            System.err.println("Failed to log info to ExtentReport: " + e.getMessage());
        }
    }

    /**
     * Log pass message
     */
    protected void logPass(String message) {
        System.out.println("[PASS] " + message);
        try {
            ExtentReportManager.logPass(message);
        } catch (Exception e) {
            System.err.println("Failed to log pass to ExtentReport: " + e.getMessage());
        }
    }

    /**
     * Log fail message
     */
    protected void logFail(String message) {
        System.out.println("[FAIL] " + message);
        try {
            ExtentReportManager.logFail(message);
        } catch (Exception e) {
            System.err.println("Failed to log fail to ExtentReport: " + e.getMessage());
        }
    }

    /**
     * Log skip message
     */
    protected void logSkip(String message) {
        System.out.println("[SKIP] " + message);
        try {
            ExtentReportManager.logSkip(message);
        } catch (Exception e) {
            System.err.println("Failed to log skip to ExtentReport: " + e.getMessage());
        }
    }

    /**
     * Create test in ExtentReport
     */
    protected void createTest(String testName, String description) {
        try {
            ExtentReportManager.createTest(testName, description);
        } catch (Exception e) {
            System.err.println("Failed to create test in ExtentReport: " + e.getMessage());
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        System.out.println("Cleaning up test method...");

        try {
            // Handle test result for reporting
            if (result.getStatus() == ITestResult.FAILURE) {
                System.out.println("Test failed: " + result.getName());

                // Take screenshot on failure only if driver is initialized and active
                if (isDriverActive()) {
                    String screenshotPath = TestUtils.takeScreenshot(result.getName() + "_FAILED");
                    if (screenshotPath != null) {
                        ExtentReportManager.addScreenshot(screenshotPath);
                    }
                } else {
                    System.out.println("Driver not active, skipping screenshot");
                }

                ExtentReportManager.logFail("Test failed: " + result.getThrowable().getMessage());

            } else if (result.getStatus() == ITestResult.SUCCESS) {
                System.out.println("Test passed: " + result.getName());
                ExtentReportManager.logPass("Test completed successfully");

            } else if (result.getStatus() == ITestResult.SKIP) {
                System.out.println("Test skipped: " + result.getName());
                ExtentReportManager.logSkip("Test was skipped: " + result.getThrowable().getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error in afterMethod reporting: " + e.getMessage());
        }

        try {
            // Clean up ExtentReports thread local
            ExtentReportManager.removeTest();
        } catch (Exception e) {
            System.err.println("Error removing ExtentReport test: " + e.getMessage());
        }

        try {
            // Quit driver - but only if it exists and is active
            if (driver != null) {
                System.out.println("Quitting driver with session: " + driver.getSessionId());
                AppiumConfig.quitDriver();
                driver = null; // Explicitly set to null
            }
        } catch (Exception e) {
            System.err.println("Error quitting driver: " + e.getMessage());
            // Force set driver to null even if quit failed
            driver = null;
        }

        System.out.println("Test method cleanup completed");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("=== Cleaning up Test Suite ===");

        try {
            // Flush ExtentReports
            ExtentReportManager.flushReports();
        } catch (Exception e) {
            System.err.println("Error flushing ExtentReports: " + e.getMessage());
        }

        try {
            // Stop Appium server
            AppiumConfig.stopAppiumServer();
        } catch (Exception e) {
            System.err.println("Error stopping Appium server: " + e.getMessage());
        }

        System.out.println("Test suite cleanup completed");
    }

    /**
     * Take screenshot and add to report with driver validation
     */
    protected void takeScreenshot(String description) {
        try {
            if (!isDriverActive()) {
                System.err.println("Cannot take screenshot '" + description + "' - driver not active");
                logInfo("Screenshot skipped due to inactive driver: " + description);
                return;
            }

            String screenshotPath = TestUtils.takeScreenshot(description);
            if (screenshotPath != null) {
                ExtentReportManager.addScreenshot(screenshotPath);
                logInfo("Screenshot captured: " + description);
            } else {
                System.err.println("Screenshot path is null for: " + description);
            }
        } catch (Exception e) {
            System.err.println("Error taking screenshot '" + description + "': " + e.getMessage());
            logInfo("Screenshot failed: " + description + " - " + e.getMessage());
        }
    }

    /**
     * Wait for specified duration
     */
    protected void waitFor(long milliseconds) {
        TestUtils.waitFor(milliseconds);
    }

    /**
     * Get current driver instance with validation
     */
    protected AndroidDriver getDriver() {
        if (driver == null) {
            System.out.println("Driver is null, attempting to get from AppiumConfig...");
            driver = AppiumConfig.getDriver();
        }
        return driver;
    }
}