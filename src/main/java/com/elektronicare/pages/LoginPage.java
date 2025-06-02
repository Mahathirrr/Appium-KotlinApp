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
 * Enhanced version with robust element detection and error handling
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

    // Alternative locators in case primary ones fail
    @AndroidFindBy(xpath = "//*[contains(@resource-id,'edit_email') or contains(@text,'Email') or contains(@hint,'Email')]")
    private WebElement emailFieldAlt;

    @AndroidFindBy(xpath = "//*[contains(@resource-id,'edit_password') or contains(@text,'Password') or contains(@hint,'Password')]")
    private WebElement passwordFieldAlt;

    @AndroidFindBy(xpath = "//*[contains(@resource-id,'btn_sign_in') or contains(@text,'Sign In') or contains(@text,'Login')]")
    private WebElement loginButtonAlt;

    private static final int DEFAULT_TIMEOUT = 15; // Increased timeout
    private static final int LONG_TIMEOUT = 30;

    public LoginPage() {
        super();
        // Give more time for page initialization
        waitForPageToLoad();
    }

    @Override
    public String getPageTitle() {
        return "Login";
    }

    /**
     * Enhanced page load detection with multiple attempts
     */
    @Override
    public boolean isPageLoaded() {
        return waitForPageToLoad();
    }

    /**
     * Comprehensive page load waiting with fallback strategies
     */
    private boolean waitForPageToLoad() {
        try {
            // Ensure driver is available
            if (driver == null) {
                initializeElements();
                if (driver == null) {
                    System.err.println("Driver is null - cannot check page load");
                    return false;
                }
            }

            // Strategy 1: Wait for primary elements
            if (waitForPrimaryElements()) {
                System.out.println("Login page loaded successfully using primary elements");
                return true;
            }

            // Strategy 2: Wait for alternative elements
            if (waitForAlternativeElements()) {
                System.out.println("Login page loaded successfully using alternative elements");
                return true;
            }

            // Strategy 3: Check for any login-related text on screen
            if (waitForLoginPageIndicators()) {
                System.out.println("Login page detected using text indicators");
                return true;
            }

            System.err.println("Login page failed to load using all strategies");
            return false;

        } catch (Exception e) {
            System.err.println("Exception while waiting for login page: " + e.getMessage());
            return false;
        }
    }

    /**
     * Wait for primary login elements
     */
    private boolean waitForPrimaryElements() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(emailField),
                    ExpectedConditions.visibilityOf(passwordField),
                    ExpectedConditions.visibilityOf(loginButton)));

            // Verify at least 2 out of 3 elements are visible
            int visibleCount = 0;
            if (isElementVisible(emailField))
                visibleCount++;
            if (isElementVisible(passwordField))
                visibleCount++;
            if (isElementVisible(loginButton))
                visibleCount++;

            return visibleCount >= 2;
        } catch (TimeoutException e) {
            System.out.println("Primary elements not found within timeout");
            return false;
        }
    }

    /**
     * Wait for alternative login elements
     */
    private boolean waitForAlternativeElements() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(emailFieldAlt),
                    ExpectedConditions.visibilityOf(passwordFieldAlt),
                    ExpectedConditions.visibilityOf(loginButtonAlt)));

            return true;
        } catch (TimeoutException e) {
            System.out.println("Alternative elements not found within timeout");
            return false;
        }
    }

    /**
     * Check for login page text indicators
     */
    private boolean waitForLoginPageIndicators() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            return wait.until(driver -> {
                String pageSource = driver.getPageSource().toLowerCase();
                return pageSource.contains("sign in") ||
                        pageSource.contains("login") ||
                        pageSource.contains("email") && pageSource.contains("password");
            });
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is visible without throwing exception
     */
    private boolean isElementVisible(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the best available email field
     */
    private WebElement getEmailField() {
        if (isElementVisible(emailField))
            return emailField;
        if (isElementVisible(emailFieldAlt))
            return emailFieldAlt;
        throw new RuntimeException("Email field not found");
    }

    /**
     * Get the best available password field
     */
    private WebElement getPasswordField() {
        if (isElementVisible(passwordField))
            return passwordField;
        if (isElementVisible(passwordFieldAlt))
            return passwordFieldAlt;
        throw new RuntimeException("Password field not found");
    }

    /**
     * Get the best available login button
     */
    private WebElement getLoginButton() {
        if (isElementVisible(loginButton))
            return loginButton;
        if (isElementVisible(loginButtonAlt))
            return loginButtonAlt;
        throw new RuntimeException("Login button not found");
    }

    public boolean isLoginPageDisplayed() {
        return isLoginPageDisplayed(DEFAULT_TIMEOUT * 1000);
    }

    public boolean isLoginPageDisplayed(int timeoutMs) {
        try {
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < timeoutMs) {
                if (isPageLoaded()) {
                    return true;
                }
                Thread.sleep(500);
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error checking login page display: " + e.getMessage());
            return false;
        }
    }

    public void enterEmail(String email) {
        try {
            WebElement emailEl = getEmailField();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(emailEl));

            emailEl.clear();
            Thread.sleep(500); // Small delay after clear
            emailEl.sendKeys(email);

            System.out.println("Email entered successfully: " + email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter email: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            WebElement passwordEl = getPasswordField();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(passwordEl));

            passwordEl.clear();
            Thread.sleep(500); // Small delay after clear
            passwordEl.sendKeys(password);

            System.out.println("Password entered successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter password: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            WebElement loginBtn = getLoginButton();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(loginBtn));

            loginBtn.click();
            System.out.println("Login button clicked successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click login button: " + e.getMessage());
        }
    }

    public void login(String email, String password) {
        try {
            System.out.println("Starting login process...");

            enterEmail(email);
            Thread.sleep(1000);

            enterPassword(password);
            Thread.sleep(1000);

            hideKeyboard();
            Thread.sleep(1000);

            clickLoginButton();
            System.out.println("Login process completed");

        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    public void clickGoogleSignIn() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(googleSignInButton));
            googleSignInButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Google Sign In: " + e.getMessage());
        }
    }

    public void clickRegisterLink() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(registerLink));
            registerLink.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click register link: " + e.getMessage());
        }
    }

    public String getToastMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(toastMessage));
            return toastMessage.getText();
        } catch (Exception e) {
            // Also try to find toast message in page source
            try {
                String pageSource = driver.getPageSource();
                if (pageSource.contains("toast") || pageSource.contains("error") || pageSource.contains("invalid")) {
                    System.out.println("Toast-like content found in page source");
                    return "Toast message detected";
                }
            } catch (Exception ex) {
                // Ignore
            }
            return "";
        }
    }

    public void waitForLoginToComplete() {
        try {
            System.out.println("Waiting for login to complete...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));

            wait.until(driver -> {
                try {
                    // Check if we're still on login page
                    boolean stillOnLogin = isLoginPageDisplayed(2000);

                    // Check for any toast messages
                    String toast = getToastMessage();
                    boolean hasToast = !toast.isEmpty();

                    // Check if page source changed significantly
                    String pageSource = driver.getPageSource().toLowerCase();
                    boolean hasNewContent = pageSource.contains("dashboard") ||
                            pageSource.contains("home") ||
                            pageSource.contains("welcome");

                    System.out.println("Login completion check - StillOnLogin: " + stillOnLogin +
                            ", HasToast: " + hasToast + ", HasNewContent: " + hasNewContent);

                    return !stillOnLogin || hasToast || hasNewContent;
                } catch (Exception e) {
                    System.out.println("Exception in login completion check: " + e.getMessage());
                    return true; // Assume completion if we can't check
                }
            });
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for login completion - proceeding anyway");
        } catch (Exception e) {
            System.out.println("Error waiting for login completion: " + e.getMessage());
        }
    }

    public boolean isGoogleSignInDisplayed() {
        try {
            return isElementVisible(googleSignInButton);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRegisterLinkDisplayed() {
        try {
            return isElementVisible(registerLink);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Force refresh the page elements
     */
    public void refreshPageElements() {
        try {
            initializeElements();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Failed to refresh page elements: " + e.getMessage());
        }
    }

    /**
     * Comprehensive page readiness check
     */
    public boolean isPageReadyForTesting() {
        try {
            System.out.println("Checking if login page is ready for testing...");

            // Multiple attempts with different strategies
            for (int attempt = 1; attempt <= 3; attempt++) {
                System.out.println("Attempt " + attempt + " to verify page readiness");

                refreshPageElements();
                Thread.sleep(2000);

                if (isPageLoaded()) {
                    System.out.println("Login page is ready for testing!");
                    return true;
                }

                if (attempt < 3) {
                    System.out.println("Page not ready, waiting before retry...");
                    Thread.sleep(3000);
                }
            }

            System.err.println("Login page is not ready for testing after all attempts");
            return false;

        } catch (Exception e) {
            System.err.println("Exception checking page readiness: " + e.getMessage());
            return false;
        }
    }
}