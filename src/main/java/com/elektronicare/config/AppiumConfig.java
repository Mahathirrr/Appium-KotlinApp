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
        if (service == null || !service.isRunning()) {
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.withIPAddress("127.0.0.1");
            builder.usingPort(4723);
            builder.withArgument(() -> "--allow-insecure", "chromedriver_autodownload");

            service = AppiumDriverLocalService.buildService(builder);
            service.start();

            System.out.println("Appium Server started successfully");
        }
    }

    /**
     * Stop Appium server
     */
    public static void stopAppiumServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("Appium Server stopped successfully");
        }
    }

    /**
     * Initialize Android driver with capabilities
     */
    public static AndroidDriver initializeDriver() {
        try {
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

            // Additional capabilities
            options.setNoReset(false); // Reset app state
            options.setFullReset(false); // Don't uninstall app
            options.setNewCommandTimeout(Duration.ofSeconds(300));

            // Initialize driver
            driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);

            // Set timeouts
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);

            System.out.println("Android Driver initialized successfully");
            return driver;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL: " + APPIUM_SERVER_URL, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Android driver", e);
        }
    }

    /**
     * Get current driver instance
     */
    public static AndroidDriver getDriver() {
        if (driver == null) {
            throw new RuntimeException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    /**
     * Quit driver and cleanup
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("Android Driver quit successfully");
        }
    }

    /**
     * Check if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return driver != null;
    }
}
