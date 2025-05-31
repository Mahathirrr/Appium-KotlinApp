package com.elektronicare.utils;

import com.elektronicare.config.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for common test operations
 */
public class TestUtils {
    
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    /**
     * Wait for element to be visible
     */
    public static WebElement waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(AppiumConfig.getDriver(), AppiumConfig.EXPLICIT_WAIT);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable
     */
    public static WebElement waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(AppiumConfig.getDriver(), AppiumConfig.EXPLICIT_WAIT);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Safe click on element with wait
     */
    public static void safeClick(WebElement element) {
        waitForElementToBeClickable(element).click();
    }
    
    /**
     * Safe send keys with wait
     */
    public static void safeSendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element).clear();
        element.sendKeys(text);
    }
    
    /**
     * Get element text safely
     */
    public static String getElementText(WebElement element) {
        return waitForElementToBeVisible(element).getText();
    }
    
    /**
     * Check if element is displayed
     */
    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Take screenshot and save to file
     */
    public static String takeScreenshot(String testName) {
        try {
            AndroidDriver driver = AppiumConfig.getDriver();
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            String fileName = testName + "_" + timestamp + ".png";
            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + fileName;
            
            File destFile = new File(screenshotPath);
            destFile.getParentFile().mkdirs(); // Create directories if they don't exist
            
            FileUtils.copyFile(sourceFile, destFile);
            System.out.println("Screenshot saved: " + screenshotPath);
            
            return screenshotPath;
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Wait for specified duration
     */
    public static void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Scroll down on the screen
     */
    public static void scrollDown() {
        AndroidDriver driver = AppiumConfig.getDriver();
        org.openqa.selenium.Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.8);
        int endY = (int) (size.getHeight() * 0.2);
        
        // Use TouchAction for scrolling
        io.appium.java_client.TouchAction touchAction = new io.appium.java_client.TouchAction(driver);
        touchAction.press(io.appium.java_client.touch.offset.PointOption.point(startX, startY))
                   .waitAction(io.appium.java_client.touch.WaitOptions.waitOptions(java.time.Duration.ofMillis(1000)))
                   .moveTo(io.appium.java_client.touch.offset.PointOption.point(startX, endY))
                   .release()
                   .perform();
    }
    
    /**
     * Scroll up on the screen
     */
    public static void scrollUp() {
        AndroidDriver driver = AppiumConfig.getDriver();
        org.openqa.selenium.Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.2);
        int endY = (int) (size.getHeight() * 0.8);
        
        // Use TouchAction for scrolling
        io.appium.java_client.TouchAction touchAction = new io.appium.java_client.TouchAction(driver);
        touchAction.press(io.appium.java_client.touch.offset.PointOption.point(startX, startY))
                   .waitAction(io.appium.java_client.touch.WaitOptions.waitOptions(java.time.Duration.ofMillis(1000)))
                   .moveTo(io.appium.java_client.touch.offset.PointOption.point(startX, endY))
                   .release()
                   .perform();
    }
    
    /**
     * Hide keyboard if visible
     */
    public static void hideKeyboard() {
        try {
            AndroidDriver driver = AppiumConfig.getDriver();
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
            }
        } catch (Exception e) {
            // Keyboard might not be visible, ignore
        }
    }
    
    /**
     * Go back using Android back button
     */
    public static void goBack() {
        AppiumConfig.getDriver().navigate().back();
    }
    
    /**
     * Perform swipe gesture
     */
    public static void performSwipe(int startX, int startY, int endX, int endY) {
        AndroidDriver driver = AppiumConfig.getDriver();
        io.appium.java_client.TouchAction touchAction = new io.appium.java_client.TouchAction(driver);
        touchAction.press(io.appium.java_client.touch.offset.PointOption.point(startX, startY))
                   .waitAction(io.appium.java_client.touch.WaitOptions.waitOptions(java.time.Duration.ofMillis(1000)))
                   .moveTo(io.appium.java_client.touch.offset.PointOption.point(endX, endY))
                   .release()
                   .perform();
    }
    
    /**
     * Scroll to element by text
     */
    public static void scrollToElement(String text) {
        AndroidDriver driver = AppiumConfig.getDriver();
        try {
            // Try to find element first
            WebElement element = driver.findElement(org.openqa.selenium.By.xpath("//*[@text='" + text + "']"));
            if (element.isDisplayed()) {
                return; // Element already visible
            }
        } catch (Exception e) {
            // Element not found or not visible, continue scrolling
        }
        
        // Scroll down to find element
        for (int i = 0; i < 5; i++) {
            try {
                WebElement element = driver.findElement(org.openqa.selenium.By.xpath("//*[@text='" + text + "']"));
                if (element.isDisplayed()) {
                    return; // Found element
                }
            } catch (Exception e) {
                // Continue scrolling
            }
            scrollDown();
            waitFor(1000);
        }
    }
}