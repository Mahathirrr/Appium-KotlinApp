package com.elektronicare.pages;

import com.elektronicare.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * Page Object for Profile Screen
 */
public class ProfilePage extends BasePage {
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/logout_button")
    private WebElement logoutButton;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/profile_image")
    private WebElement profileImage;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/user_name")
    private WebElement userName;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/user_email")
    private WebElement userEmail;
    
    // Bottom navigation
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_profile")
    private WebElement profileNavButton;
    
    public ProfilePage() {
        super();
    }
    
    @Override
    public String getPageTitle() {
        return "Profile";
    }
    
    @Override
    public boolean isPageLoaded() {
        initializeElements();
        return isProfilePageDisplayed();
    }
    
    public boolean isProfilePageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(logoutButton));
            return logoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click logout button to sign out
     */
    public void clickLogout() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        TestUtils.safeClick(logoutButton);
    }
    
    /**
     * Check if logout button is displayed
     */
    public boolean isLogoutButtonDisplayed() {
        return TestUtils.isElementDisplayed(logoutButton);
    }
    
    /**
     * Check if profile image is displayed
     */
    public boolean isProfileImageDisplayed() {
        return TestUtils.isElementDisplayed(profileImage);
    }
    
    /**
     * Get user name text
     */
    public String getUserName() {
        initializeElements();
        return TestUtils.getElementText(userName);
    }
    
    /**
     * Get user email text
     */
    public String getUserEmail() {
        initializeElements();
        return TestUtils.getElementText(userEmail);
    }
    
    /**
     * Navigate to profile page via bottom navigation
     */
    public void navigateToProfile() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(profileNavButton));
        TestUtils.safeClick(profileNavButton);
    }
}