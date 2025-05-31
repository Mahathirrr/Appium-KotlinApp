package com.elektronicare.pages;

import com.elektronicare.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Page Object for Login Screen
 */
public class LoginPage extends BasePage {

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/edit_email")
    private WebElement emailField;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/edit_password")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/btn_sign_in")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/btn_google_signin")
    private WebElement googleSignInButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/link_sign_up")
    private WebElement registerLink;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/back_button")
    private WebElement backButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/toggle_password_visibility")
    private WebElement passwordToggle;

    // Error message elements
    @AndroidFindBy(xpath = "//android.widget.Toast")
    private WebElement toastMessage;

    /**
     * Check if login page is loaded
     */
    @Override
    public boolean isPageLoaded() {
        initializeElements();
        try {
            return TestUtils.isElementDisplayed(emailField) &&
                    TestUtils.isElementDisplayed(passwordField) &&
                    TestUtils.isElementDisplayed(loginButton);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if login page is displayed (alias for isPageLoaded)
     */
    public boolean isLoginPageDisplayed() {
        initializeElements();
        return isPageLoaded();
    }

    /**
     * Get page title
     */
    @Override
    public String getPageTitle() {
        return "Login";
    }

    /**
     * Enter email address
     */
    public void enterEmail(String email) {
        initializeElements();
        TestUtils.safeSendKeys(emailField, email);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        initializeElements();
        TestUtils.safeSendKeys(passwordField, password);
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        initializeElements();
        TestUtils.safeClick(loginButton);
    }

    /**
     * Click Google Sign In button
     */
    public void clickGoogleSignIn() {
        initializeElements();
        TestUtils.safeClick(googleSignInButton);
    }

    /**
     * Click register link
     */
    public void clickRegisterLink() {
        initializeElements();
        TestUtils.safeClick(registerLink);
    }

    /**
     * Click back button
     */
    public void clickBackButton() {
        initializeElements();
        TestUtils.safeClick(backButton);
    }

    /**
     * Click password visibility toggle
     */
    public void clickPasswordToggle() {
        initializeElements();
        TestUtils.safeClick(passwordToggle);
    }

    /**
     * Perform login with email and password
     */
    public void login(String email, String password) {
        initializeElements();
        enterEmail(email);
        enterPassword(password);
        hideKeyboard();
        clickLoginButton();
        initializeElements();
    }

    /**
     * Check if back button is displayed
     */
    public boolean isBackButtonDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(backButton);
    }

    /**
     * Check if password toggle is displayed
     */
    public boolean isPasswordToggleDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(passwordToggle);
    }

    /**
     * Check if email field is displayed
     */
    public boolean isEmailFieldDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(emailField);
    }

    /**
     * Check if password field is displayed
     */
    public boolean isPasswordFieldDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(passwordField);
    }

    /**
     * Check if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        initializeElements();
        return loginButton.isEnabled();
    }

    /**
     * Check if Google Sign In button is displayed
     */
    public boolean isGoogleSignInDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(googleSignInButton);
    }

    /**
     * Check if register link is displayed
     */
    public boolean isRegisterLinkDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(registerLink);
    }

    /**
     * Get toast message text (if visible)
     */
    public String getToastMessage() {
        initializeElements();
        try {
            if (TestUtils.isElementDisplayed(toastMessage)) {
                return TestUtils.getElementText(toastMessage);
            }
        } catch (Exception e) {
            // Toast might not be visible
        }
        return "";
    }

    /**
     * Wait for login to complete (page to change)
     */
    public void waitForLoginToComplete() {
        initializeElements();
        // Wait for page transition
        waitFor(2000);

        // Wait until login page is no longer displayed
        int maxWaitTime = 5000; // 5 seconds max
        int waitInterval = 500; // Check every 500ms
        int totalWaitTime = 0;

        while (isPageLoaded() && totalWaitTime < maxWaitTime) {
            waitFor(waitInterval);
            totalWaitTime += waitInterval;
        }
    }
}