package com.elektronicare.pages;

import com.elektronicare.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;

/**
 * Page Object for Login Screen
 * Improved version with better error handling and stability
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

    private static final int DEFAULT_TIMEOUT = 10000; // 10 seconds
    private static final int DEFAULT_RETRY_COUNT = 5;

    /**
     * Constructor with proper initialization
     */
    public LoginPage() {
        super();
        // Ensure elements are initialized when page object is created
        safeInitializeElements();
    }

    /**
     * Safe initialization of elements with retry mechanism
     */
    private void safeInitializeElements() {
        int retryCount = 0;
        while (retryCount < DEFAULT_RETRY_COUNT) {
            try {
                initializeElements();
                return; // Success, exit the loop
            } catch (Exception e) {
                retryCount++;
                if (retryCount >= DEFAULT_RETRY_COUNT) {
                    throw new RuntimeException(
                            "Failed to initialize elements after " + DEFAULT_RETRY_COUNT + " attempts", e);
                }
                try {
                    Thread.sleep(1500); // Wait before retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during element initialization", ie);
                }
            }
        }
    }

    /**
     * Check if login page is loaded with improved error handling
     */
    @Override
    public boolean isPageLoaded() {
        try {
            safeInitializeElements();

            // Check multiple key elements to ensure page is properly loaded
            boolean emailDisplayed = TestUtils.isElementDisplayed(emailField);
            boolean passwordDisplayed = TestUtils.isElementDisplayed(passwordField);
            boolean loginButtonDisplayed = TestUtils.isElementDisplayed(loginButton);

            return emailDisplayed && passwordDisplayed && loginButtonDisplayed;

        } catch (Exception e) {
            // Log the exception but don't throw it to avoid breaking test flow
            System.err.println("Error checking if login page is loaded: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if login page is displayed with timeout
     */
    public boolean isLoginPageDisplayed() {
        return isLoginPageDisplayed(DEFAULT_TIMEOUT);
    }

    /**
     * Check if login page is displayed with custom timeout
     */
    public boolean isLoginPageDisplayed(int timeoutMs) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < timeoutMs) {
            if (isPageLoaded()) {
                return true;
            }
            try {
                Thread.sleep(500); // Check every 500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    /**
     * Get page title
     */
    @Override
    public String getPageTitle() {
        return "Login";
    }

    /**
     * Enter email address with validation
     */
    public void enterEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        try {
            safeInitializeElements();
            if (!TestUtils.isElementDisplayed(emailField)) {
                throw new RuntimeException("Email field is not displayed");
            }
            TestUtils.safeSendKeys(emailField, email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter email: " + email, e);
        }
    }

    /**
     * Enter password with validation
     */
    public void enterPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        try {
            safeInitializeElements();
            if (!TestUtils.isElementDisplayed(passwordField)) {
                throw new RuntimeException("Password field is not displayed");
            }
            TestUtils.safeSendKeys(passwordField, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter password", e);
        }
    }

    /**
     * Click login button with validation
     */
    public void clickLoginButton() {
        try {
            safeInitializeElements();
            if (!TestUtils.isElementDisplayed(loginButton)) {
                throw new RuntimeException("Login button is not displayed");
            }
            if (!loginButton.isEnabled()) {
                throw new RuntimeException("Login button is not enabled");
            }
            TestUtils.safeClick(loginButton);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click login button", e);
        }
    }

    /**
     * Click Google Sign In button with validation
     */
    public void clickGoogleSignIn() {
        try {
            safeInitializeElements();
            if (!isGoogleSignInDisplayed()) {
                throw new RuntimeException("Google Sign In button is not displayed");
            }
            TestUtils.safeClick(googleSignInButton);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Google Sign In button", e);
        }
    }

    /**
     * Click register link with validation
     */
    public void clickRegisterLink() {
        try {
            safeInitializeElements();
            if (!isRegisterLinkDisplayed()) {
                throw new RuntimeException("Register link is not displayed");
            }
            TestUtils.safeClick(registerLink);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click register link", e);
        }
    }

    /**
     * Click back button with validation
     */
    public void clickBackButton() {
        try {
            safeInitializeElements();
            if (!isBackButtonDisplayed()) {
                throw new RuntimeException("Back button is not displayed");
            }
            TestUtils.safeClick(backButton);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click back button", e);
        }
    }

    /**
     * Click password visibility toggle with validation
     */
    public void clickPasswordToggle() {
        try {
            safeInitializeElements();
            if (!isPasswordToggleDisplayed()) {
                throw new RuntimeException("Password toggle is not displayed");
            }
            TestUtils.safeClick(passwordToggle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click password toggle", e);
        }
    }

    /**
     * Perform login with email and password - improved version
     */
    public void login(String email, String password) {
        if (!isPageLoaded()) {
            throw new RuntimeException("Login page is not loaded - cannot perform login");
        }

        try {
            enterEmail(email);
            enterPassword(password);
            hideKeyboard();
            clickLoginButton();

            // Re-initialize elements after login attempt
            safeInitializeElements();
        } catch (Exception e) {
            throw new RuntimeException("Login failed for email: " + email, e);
        }
    }

    /**
     * Check if back button is displayed
     */
    public boolean isBackButtonDisplayed() {
        try {
            safeInitializeElements();
            return TestUtils.isElementDisplayed(backButton);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if password toggle is displayed
     */
    public boolean isPasswordToggleDisplayed() {
        try {
            safeInitializeElements();
            return TestUtils.isElementDisplayed(passwordToggle);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if email field is displayed
     */
    public boolean isEmailFieldDisplayed() {
        try {
            safeInitializeElements();
            return TestUtils.isElementDisplayed(emailField);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if password field is displayed
     */
    public boolean isPasswordFieldDisplayed() {
        try {
            safeInitializeElements();
            return TestUtils.isElementDisplayed(passwordField);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        try {
            safeInitializeElements();
            return TestUtils.isElementDisplayed(loginButton) && loginButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Google Sign In button is displayed
     */
    public boolean isGoogleSignInDisplayed() {
        try {
            safeInitializeElements();
            return TestUtils.isElementDisplayed(googleSignInButton);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if register link is displayed
     */
    public boolean isRegisterLinkDisplayed() {
        try {
            safeInitializeElements();
            return TestUtils.isElementDisplayed(registerLink);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get toast message text with improved error handling
     */
    public String getToastMessage() {
        try {
            safeInitializeElements();
            if (TestUtils.isElementDisplayed(toastMessage)) {
                String message = TestUtils.getElementText(toastMessage);
                return message != null ? message : "";
            }
        } catch (Exception e) {
            // Toast might not be visible or accessible
            System.err.println("Error getting toast message: " + e.getMessage());
        }
        return "";
    }

    /**
     * Wait for login to complete with improved logic
     */
    public void waitForLoginToComplete() {
        waitForLoginToComplete(DEFAULT_TIMEOUT);
    }

    /**
     * Wait for login to complete with custom timeout
     */
    public void waitForLoginToComplete(int timeoutMs) {
        try {
            safeInitializeElements();

            // Wait for page transition with proper timeout handling
            int waitInterval = 500; // Check every 500ms
            int totalWaitTime = 0;

            while (isPageLoaded() && totalWaitTime < timeoutMs) {
                try {
                    Thread.sleep(waitInterval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted while waiting for login to complete", e);
                }
                totalWaitTime += waitInterval;
            }

            if (totalWaitTime >= timeoutMs && isPageLoaded()) {
                throw new TimeoutException("Login did not complete within " + timeoutMs + "ms");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error waiting for login to complete", e);
        }
    }

    /**
     * Validate that all essential elements are present and functional
     */
    public boolean validateLoginPageElements() {
        try {
            return isEmailFieldDisplayed() &&
                    isPasswordFieldDisplayed() &&
                    isLoginButtonEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}