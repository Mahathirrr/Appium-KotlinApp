package com.elektronicare.pages;

import com.elektronicare.config.AppiumConfig;
import com.elektronicare.utils.TestUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

/**
 * Base page class with common functionality for all page objects
 */
public abstract class BasePage {
    
    protected AndroidDriver driver;
    
    public BasePage() {
        try {
            this.driver = AppiumConfig.getDriver();
            if (this.driver != null) {
                PageFactory.initElements(new AppiumFieldDecorator(driver), this);
            }
        } catch (RuntimeException e) {
            System.err.println("Warning: Driver not initialized when creating page object: " + e.getMessage());
            // Driver will be null, but we can still create the page object
        }
    }
    
    /**
     * Initialize page elements if driver becomes available
     */
    protected void initializeElements() {
        if (this.driver == null) {
            try {
                this.driver = AppiumConfig.getDriver();
                if (this.driver != null) {
                    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
                }
            } catch (RuntimeException e) {
                System.err.println("Driver still not available: " + e.getMessage());
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
        return TestUtils.takeScreenshot(this.getClass().getSimpleName());
    }
    
    /**
     * Go back using Android back button
     */
    public void goBack() {
        TestUtils.goBack();
    }
    
    /**
     * Hide keyboard if visible
     */
    public void hideKeyboard() {
        TestUtils.hideKeyboard();
    }
    
    /**
     * Wait for specified duration
     */
    public void waitFor(long milliseconds) {
        TestUtils.waitFor(milliseconds);
    }
    
    /**
     * Scroll down on the page
     */
    public void scrollDown() {
        TestUtils.scrollDown();
    }
    
    /**
     * Scroll up on the page
     */
    public void scrollUp() {
        TestUtils.scrollUp();
    }
    
    /**
     * Perform swipe gesture
     */
    public void performSwipe(int startX, int startY, int endX, int endY) {
        TestUtils.performSwipe(startX, startY, endX, endY);
    }
    
    /**
     * Scroll to element by text
     */
    public void scrollToElement(String text) {
        TestUtils.scrollToElement(text);
    }
}