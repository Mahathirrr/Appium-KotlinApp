package com.elektronicare.pages;

import com.elektronicare.config.AppiumConfig;
import com.elektronicare.utils.TestUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

/**
 * Enhanced base page class with improved initialization and error handling
 */
public abstract class BasePage {

    protected AndroidDriver driver;
    private boolean elementsInitialized = false;

    public BasePage() {
        initializeDriver();
    }

    /**
     * Initialize driver with retry mechanism
     */
    private void initializeDriver() {
        try {
            this.driver = AppiumConfig.getDriver();
            if (this.driver != null) {
                PageFactory.initElements(new AppiumFieldDecorator(driver), this);
                elementsInitialized = true;
                System.out.println("Page elements initialized successfully for " + this.getClass().getSimpleName());
            } else {
                System.err.println("Warning: Driver is null when creating " + this.getClass().getSimpleName());
            }
        } catch (Exception e) {
            System.err.println(
                    "Warning: Failed to initialize " + this.getClass().getSimpleName() + ": " + e.getMessage());
            // Don't throw exception, allow page creation
        }
    }

    /**
     * Enhanced element initialization with multiple attempts
     */
    protected void initializeElements() {
        if (elementsInitialized && this.driver != null) {
            return; // Already initialized
        }

        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println(
                        "Initializing elements for " + this.getClass().getSimpleName() + " (attempt " + attempt + ")");

                this.driver = AppiumConfig.getDriver();
                if (this.driver != null) {
                    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
                    elementsInitialized = true;
                    System.out.println("Elements initialized successfully on attempt " + attempt);
                    return;
                } else {
                    System.err.println("Driver is null on attempt " + attempt);
                }
            } catch (Exception e) {
                System.err.println("Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        if (!elementsInitialized) {
            System.err.println("Failed to initialize elements after " + maxAttempts + " attempts");
        }
    }

    /**
     * Check if driver and elements are properly initialized
     */
    protected boolean isInitialized() {
        return driver != null && elementsInitialized;
    }

    /**
     * Ensure driver is available for operations
     */
    protected void ensureDriverAvailable() {
        if (!isInitialized()) {
            initializeElements();
            if (!isInitialized()) {
                throw new RuntimeException("Driver not available for " + this.getClass().getSimpleName());
            }
        }
    }

    /**
     * Wait for page to load (to be implemented by subclasses)
     */
    public abstract boolean isPageLoaded();

    /**
     * Get page title or identifier
     */
    public abstract String getPageTitle();

    /**
     * Take screenshot of current page
     */
    public String takeScreenshot() {
        try {
            return TestUtils.takeScreenshot(this.getClass().getSimpleName());
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Go back using Android back button
     */
    public void goBack() {
        try {
            TestUtils.goBack();
        } catch (Exception e) {
            System.err.println("Failed to go back: " + e.getMessage());
        }
    }

    /**
     * Hide keyboard if visible
     */
    public void hideKeyboard() {
        try {
            TestUtils.hideKeyboard();
        } catch (Exception e) {
            System.err.println("Failed to hide keyboard: " + e.getMessage());
        }
    }

    /**
     * Wait for specified duration
     */
    public void waitFor(long milliseconds) {
        try {
            TestUtils.waitFor(milliseconds);
        } catch (Exception e) {
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }

    /**
     * Scroll down on the page
     */
    public void scrollDown() {
        try {
            TestUtils.scrollDown();
        } catch (Exception e) {
            System.err.println("Failed to scroll down: " + e.getMessage());
        }
    }

    /**
     * Scroll up on the page
     */
    public void scrollUp() {
        try {
            TestUtils.scrollUp();
        } catch (Exception e) {
            System.err.println("Failed to scroll up: " + e.getMessage());
        }
    }

    /**
     * Perform swipe gesture
     */
    public void performSwipe(int startX, int startY, int endX, int endY) {
        try {
            TestUtils.performSwipe(startX, startY, endX, endY);
        } catch (Exception e) {
            System.err.println("Failed to perform swipe: " + e.getMessage());
        }
    }

    /**
     * Scroll to element by text
     */
    public void scrollToElement(String text) {
        try {
            TestUtils.scrollToElement(text);
        } catch (Exception e) {
            System.err.println("Failed to scroll to element: " + e.getMessage());
        }
    }

    /**
     * Get current driver instance
     */
    protected AndroidDriver getDriver() {
        ensureDriverAvailable();
        return driver;
    }
}