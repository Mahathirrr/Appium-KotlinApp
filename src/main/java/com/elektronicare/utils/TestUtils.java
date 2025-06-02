package com.elektronicare.utils;

import com.elektronicare.config.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;

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
     * Get driver safely with validation
     */
    private static AndroidDriver getDriverSafely() {
        try {
            return AppiumConfig.getDriver();
        } catch (Exception e) {
            System.err.println("Driver not available: " + e.getMessage());
            return null;
        }
    }

    /**
     * Wait for element to be visible with safe driver check
     */
    public static WebElement waitForElementToBeVisible(WebElement element) {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            throw new RuntimeException("Driver not available for waiting element visibility");
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, AppiumConfig.EXPLICIT_WAIT);
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            System.err.println("Element not visible within timeout: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error waiting for element visibility: " + e.getMessage());
            throw new RuntimeException("Failed to wait for element visibility", e);
        }
    }

    /**
     * Wait for element to be clickable with safe driver check
     */
    public static WebElement waitForElementToBeClickable(WebElement element) {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            throw new RuntimeException("Driver not available for waiting element clickability");
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, AppiumConfig.EXPLICIT_WAIT);
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            System.err.println("Element not clickable within timeout: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error waiting for element clickability: " + e.getMessage());
            throw new RuntimeException("Failed to wait for element clickability", e);
        }
    }

    /**
     * Safe click on element with wait and validation
     */
    public static void safeClick(WebElement element) {
        try {
            if (element == null) {
                throw new IllegalArgumentException("Element cannot be null");
            }

            WebElement clickableElement = waitForElementToBeClickable(element);
            clickableElement.click();
            System.out.println("Successfully clicked element");

        } catch (Exception e) {
            System.err.println("Failed to click element: " + e.getMessage());
            throw new RuntimeException("Safe click failed", e);
        }
    }

    /**
     * Safe send keys with wait and validation
     */
    public static void safeSendKeys(WebElement element, String text) {
        try {
            if (element == null) {
                throw new IllegalArgumentException("Element cannot be null");
            }
            if (text == null) {
                text = "";
            }

            WebElement visibleElement = waitForElementToBeVisible(element);
            visibleElement.clear();
            visibleElement.sendKeys(text);
            System.out.println("Successfully sent text: " + text);

        } catch (Exception e) {
            System.err.println("Failed to send keys: " + e.getMessage());
            throw new RuntimeException("Safe send keys failed", e);
        }
    }

    /**
     * Get element text safely with validation
     */
    public static String getElementText(WebElement element) {
        try {
            if (element == null) {
                throw new IllegalArgumentException("Element cannot be null");
            }

            WebElement visibleElement = waitForElementToBeVisible(element);
            String text = visibleElement.getText();
            System.out.println("Element text retrieved: " + text);
            return text != null ? text : "";

        } catch (Exception e) {
            System.err.println("Failed to get element text: " + e.getMessage());
            return "";
        }
    }

    /**
     * Check if element is displayed safely
     */
    public static boolean isElementDisplayed(WebElement element) {
        try {
            if (element == null) {
                return false;
            }
            return element.isDisplayed();
        } catch (Exception e) {
            System.err.println("Error checking element display: " + e.getMessage());
            return false;
        }
    }

    /**
     * Take screenshot and save to file with safe driver check
     */
    public static String takeScreenshot(String testName) {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot take screenshot - driver not available");
            return null;
        }

        try {
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
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        } catch (Exception e) {
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
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }

    /**
     * Scroll down on the screen with safe driver check
     */
    public static void scrollDown() {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot scroll - driver not available");
            return;
        }

        try {
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

            System.out.println("Scroll down performed successfully");
        } catch (Exception e) {
            System.err.println("Failed to scroll down: " + e.getMessage());
        }
    }

    /**
     * Scroll up on the screen with safe driver check
     */
    public static void scrollUp() {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot scroll - driver not available");
            return;
        }

        try {
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

            System.out.println("Scroll up performed successfully");
        } catch (Exception e) {
            System.err.println("Failed to scroll up: " + e.getMessage());
        }
    }

    /**
     * Hide keyboard if visible with safe driver check
     */
    public static void hideKeyboard() {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot hide keyboard - driver not available");
            return;
        }

        try {
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
                System.out.println("Keyboard hidden successfully");
            }
        } catch (Exception e) {
            System.err.println("Error hiding keyboard (may not be visible): " + e.getMessage());
        }
    }

    /**
     * Go back using Android back button with safe driver check
     */
    public static void goBack() {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot go back - driver not available");
            return;
        }

        try {
            driver.navigate().back();
            System.out.println("Back navigation performed successfully");
        } catch (Exception e) {
            System.err.println("Failed to go back: " + e.getMessage());
        }
    }

    /**
     * Perform swipe gesture with safe driver check
     */
    public static void performSwipe(int startX, int startY, int endX, int endY) {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot perform swipe - driver not available");
            return;
        }

        try {
            io.appium.java_client.TouchAction touchAction = new io.appium.java_client.TouchAction(driver);
            touchAction.press(io.appium.java_client.touch.offset.PointOption.point(startX, startY))
                       .waitAction(io.appium.java_client.touch.WaitOptions.waitOptions(java.time.Duration.ofMillis(1000)))
                       .moveTo(io.appium.java_client.touch.offset.PointOption.point(endX, endY))
                       .release()
                       .perform();

            System.out.println("Swipe performed successfully from (" + startX + "," + startY + ") to (" + endX + "," + endY + ")");
        } catch (Exception e) {
            System.err.println("Failed to perform swipe: " + e.getMessage());
        }
    }

    /**
     * Scroll to element by text with safe driver check
     */
    public static void scrollToElement(String text) {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot scroll to element - driver not available");
            return;
        }

        if (text == null || text.trim().isEmpty()) {
            System.err.println("Cannot scroll to element - text is null or empty");
            return;
        }

        try {
            // Try to find element first
            WebElement element = driver.findElement(org.openqa.selenium.By.xpath("//*[@text='" + text + "']"));
            if (element.isDisplayed()) {
                System.out.println("Element already visible: " + text);
                return; // Element already visible
            }
        } catch (NoSuchElementException e) {
            // Element not found or not visible, continue scrolling
            System.out.println("Element not immediately visible, scrolling to find: " + text);
        }

        // Scroll down to find element
        for (int i = 0; i < 5; i++) {
            try {
                WebElement element = driver.findElement(org.openqa.selenium.By.xpath("//*[@text='" + text + "']"));
                if (element.isDisplayed()) {
                    System.out.println("Element found after " + i + " scrolls: " + text);
                    return; // Found element
                }
            } catch (NoSuchElementException e) {
                // Continue scrolling
            }
            scrollDown();
            waitFor(1000);
        }

        System.err.println("Element not found after scrolling: " + text);
    }

    /**
     * Verify driver is available and responsive
     */
    public static boolean isDriverAvailable() {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            return false;
        }

        try {
            // Test if driver is responsive
            driver.currentActivity();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Restart app if needed
     */
    public static void restartApp() {
        AndroidDriver driver = getDriverSafely();
        if (driver == null) {
            System.err.println("Cannot restart app - driver not available");
            return;
        }

        try {
            driver.terminateApp(AppiumConfig.APP_PACKAGE);
            waitFor(2000);
            driver.activateApp(AppiumConfig.APP_PACKAGE);
            System.out.println("App restarted successfully");
        } catch (Exception e) {
            System.err.println("Failed to restart app: " + e.getMessage());
        }
    }
}