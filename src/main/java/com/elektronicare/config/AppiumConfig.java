package com.elektronicare.config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Configuration class for Appium setup and driver initialization
 */
public class AppiumConfig {

    private static AndroidDriver driver;
    private static AppiumDriverLocalService service;

    // Configuration constants
    public static final String PLATFORM_NAME = "Android";
    public static final String AUTOMATION_NAME = "UiAutomator2";
    public static final String DEVICE_NAME = "Android Emulator";
    public static final String APP_PACKAGE = "com.example.elektronicarebeta1";
    public static final String APP_ACTIVITY = "com.example.elektronicarebeta1.SplashActivity";
    public static final String APK_PATH = System.getProperty("user.dir") + "/app-debug.apk";
    public static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

    // Timeout constants
    public static final Duration IMPLICIT_WAIT = Duration.ofSeconds(3);
    public static final Duration EXPLICIT_WAIT = Duration.ofSeconds(5);

    /**
     * Start Appium server programmatically
     */
    public static void startAppiumServer() {
        try {
            if (service == null || !service.isRunning()) {
                AppiumServiceBuilder builder = new AppiumServiceBuilder();
                builder.withIPAddress("127.0.0.1");
                builder.usingPort(4723);
                builder.withArgument(() -> "--allow-insecure", "chromedriver_autodownload");
                builder.withArgument(() -> "--session-override");

                service = AppiumDriverLocalService.buildService(builder);
                service.start();

                System.out.println("Appium Server started successfully");

                // Wait a bit for server to be fully ready
                Thread.sleep(2000);
            } else {
                System.out.println("Appium Server already running");
            }
        } catch (Exception e) {
            System.err.println("Failed to start Appium server: " + e.getMessage());
            throw new RuntimeException("Cannot start Appium server", e);
        }
    }

    /**
     * Stop Appium server
     */
    public static void stopAppiumServer() {
        try {
            if (service != null && service.isRunning()) {
                service.stop();
                System.out.println("Appium Server stopped successfully");
            }
        } catch (Exception e) {
            System.err.println("Error stopping Appium server: " + e.getMessage());
        } finally {
            service = null;
        }
    }

    /**
     * Initialize Android driver with capabilities
     */
    public static AndroidDriver initializeDriver() {
        try {
            // Quit existing driver if any
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception e) {
                    System.err.println("Warning: Error quitting existing driver: " + e.getMessage());
                }
                driver = null;
            }

            UiAutomator2Options options = new UiAutomator2Options();

            // Basic capabilities
            options.setPlatformName(PLATFORM_NAME);
            options.setAutomationName(AUTOMATION_NAME);
            options.setDeviceName(DEVICE_NAME);

            // App capabilities
            options.setAppPackage(APP_PACKAGE);
            options.setAppActivity(APP_ACTIVITY);

            // Check if APK file exists
            File apkFile = new File(APK_PATH);
            if (apkFile.exists()) {
                options.setApp(APK_PATH);
                System.out.println("APK found and set: " + APK_PATH);
            } else {
                System.out.println("APK not found at: " + APK_PATH);
                System.out.println("Will try to launch app if already installed");
            }

            // Additional capabilities for stability
            options.setNoReset(false); // Reset app state
            options.setFullReset(false); // Don't uninstall app
            options.setNewCommandTimeout(Duration.ofSeconds(300));
            options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(60));
            options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(60));
            options.setSkipServerInstallation(false);
            options.setSkipDeviceInitialization(false);

            // Prevent session conflicts
            options.setAutoGrantPermissions(true);
            options.setCapability("appium:sessionOverride", true);

            // Initialize driver with retry mechanism
            int maxRetries = 3;
            Exception lastException = null;

            for (int i = 0; i < maxRetries; i++) {
                try {
                    System.out.println("Attempting to initialize driver (attempt " + (i + 1) + "/" + maxRetries + ")");
                    driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);

                    // Verify driver is working
                    if (isDriverResponsive()) {
                        // Set timeouts
                        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
                        System.out.println("Android Driver initialized successfully");
                        return driver;
                    } else {
                        throw new RuntimeException("Driver initialized but not responsive");
                    }

                } catch (Exception e) {
                    lastException = e;
                    System.err.println("Driver initialization attempt " + (i + 1) + " failed: " + e.getMessage());

                    if (driver != null) {
                        try {
                            driver.quit();
                        } catch (Exception quitEx) {
                            // Ignore quit errors during retry
                        }
                        driver = null;
                    }

                    if (i < maxRetries - 1) {
                        try {
                            Thread.sleep(2000); // Wait before retry
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }

            throw new RuntimeException("Failed to initialize Android driver after " + maxRetries + " attempts",
                    lastException);

        } catch (Exception e) {
            driver = null;
            throw new RuntimeException("Failed to initialize Android driver", e);
        }
    }

    /**
     * Check if driver is responsive
     */
    private static boolean isDriverResponsive() {
        try {
            if (driver == null)
                return false;

            // Try to get current activity - this will fail if driver is not working
            String activity = driver.currentActivity();
            return activity != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get current driver instance with validation
     */
    public static AndroidDriver getDriver() {
        if (driver == null) {
            throw new RuntimeException("Driver not initialized. Call initializeDriver() first.");
        }

        // Additional check to ensure driver is still responsive
        if (!isDriverResponsive()) {
            System.err.println("Driver exists but not responsive, reinitializing...");
            driver = null;
            throw new RuntimeException("Driver not responsive. Please reinitialize.");
        }

        return driver;
    }

    /**
     * Get driver safely without throwing exception
     */
    public static AndroidDriver getDriverSafely() {
        try {
            return getDriver();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Quit driver and cleanup
     */
    public static void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();
                System.out.println("Android Driver quit successfully");
            }
        } catch (Exception e) {
            System.err.println("Error quitting driver: " + e.getMessage());
        } finally {
            driver = null;
        }
    }

    /**
     * Check if driver is initialized and responsive
     */
    public static boolean isDriverInitialized() {
        return driver != null && isDriverResponsive();
    }

    /**
     * Force reinitialize driver
     */
    public static AndroidDriver reinitializeDriver() {
        System.out.println("Force reinitializing driver...");
        quitDriver();
        return initializeDriver();
    }

    /**
     * Get driver with auto-reinitialize if needed
     */
    public static AndroidDriver getDriverWithAutoInit() {
        try {
            return getDriver();
        } catch (Exception e) {
            System.out.println("Driver not available, auto-initializing...");
            return initializeDriver();
        }
    }
}