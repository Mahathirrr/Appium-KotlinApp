package com.elektronicare.pages;

import com.elektronicare.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

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

    @AndroidFindBy(xpath = "//android.widget.Toast")
    private WebElement toastMessage;

    private static final int DEFAULT_TIMEOUT = 10000; // 10 seconds

    public LoginPage() {
        super();
        initializeElements();
    }

    @Override
    public String getPageTitle() {
        return "Login";
    }

    @Override
    public boolean isPageLoaded() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(driver -> {
                try {
                    return emailField.isDisplayed() &&
                            passwordField.isDisplayed() &&
                            loginButton.isDisplayed();
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageDisplayed() {
        return isLoginPageDisplayed(DEFAULT_TIMEOUT);
    }

    public boolean isLoginPageDisplayed(int timeoutMs) {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMs));

            // Wait for all critical elements
            wait.until(ExpectedConditions.visibilityOf(emailField));
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            wait.until(ExpectedConditions.visibilityOf(loginButton));

            return true;
        } catch (Exception e) {
            System.out.println("Login page not displayed: " + e.getMessage());
            return false;
        }
    }

    public void enterEmail(String email) {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(emailField));
            emailField.clear();
            emailField.sendKeys(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter email: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            passwordField.clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter password: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click login button: " + e.getMessage());
        }
    }

    public void login(String email, String password) {
        try {
            enterEmail(email);
            enterPassword(password);
            hideKeyboard();
            clickLoginButton();
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    public void clickGoogleSignIn() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(googleSignInButton));
            googleSignInButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Google Sign In: " + e.getMessage());
        }
    }

    public void clickRegisterLink() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(registerLink));
            registerLink.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click register link: " + e.getMessage());
        }
    }

    public String getToastMessage() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(toastMessage));
            return toastMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void waitForLoginToComplete() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(driver -> {
                try {
                    // Check for either success (page change) or error message
                    boolean onLoginPage = isLoginPageDisplayed(1000);
                    String toast = getToastMessage();
                    return !onLoginPage || !toast.isEmpty();
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
            System.out.println("Timeout waiting for login completion");
        }
    }

    public boolean isGoogleSignInDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOf(googleSignInButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRegisterLinkDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOf(registerLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}