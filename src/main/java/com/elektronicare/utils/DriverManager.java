package com.elektronicare.utils;

import com.elektronicare.config.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;

/**
 * Driver Manager for managing Appium driver instance
 */
public class DriverManager {
    
    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    
    /**
     * Set the driver instance
     */
    public static void setDriver(AndroidDriver androidDriver) {
        driver.set(androidDriver);
    }
    
    /**
     * Get the driver instance
     */
    public static AndroidDriver getDriver() {
        AndroidDriver androidDriver = driver.get();
        if (androidDriver == null) {
            // If no driver is set, get from AppiumConfig
            androidDriver = AppiumConfig.getDriver();
            setDriver(androidDriver);
        }
        return androidDriver;
    }
    
    /**
     * Remove the driver instance
     */
    public static void removeDriver() {
        driver.remove();
    }
    
    /**
     * Initialize driver using AppiumConfig
     */
    public static void initializeDriver() {
        AndroidDriver androidDriver = AppiumConfig.getDriver();
        setDriver(androidDriver);
    }
    
    /**
     * Quit the driver and remove from ThreadLocal
     */
    public static void quitDriver() {
        AndroidDriver androidDriver = driver.get();
        if (androidDriver != null) {
            androidDriver.quit();
            removeDriver();
        }
    }
}