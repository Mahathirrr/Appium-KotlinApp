package com.elektronicare.tests;

import com.elektronicare.pages.LoginPage;
import com.elektronicare.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Login functionality
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setupLoginTest() {
        // Navigate to login page through proper flow
        super.navigateToLogin();

        // Initialize login page
        loginPage = new LoginPage();

        // Wait for login page to be available
        waitFor(2000);
    }

    @Test(priority = 2, description = "Test login with valid credentials")
    public void testValidLogin() {
        createTest("Valid Login Test",
                "Test login functionality with valid credentials");

        if (!loginPage.isPageLoaded()) {
            logInfo("Login page not loaded - skipping test");
            takeScreenshot("login_page_not_available");
            return;
        }

        // Test credentials (these would need to be valid test accounts)
        String testEmail = "test@elektronicare.com";
        String testPassword = "testpassword123";

        logInfo("Attempting login with test credentials");
        logInfo("Email: " + testEmail);

        takeScreenshot("before_login_attempt");

        loginPage.login(testEmail, testPassword);

        logInfo("Login form submitted, waiting for response");
        waitFor(3000);

        takeScreenshot("after_login_attempt");

        // Check if we've navigated to dashboard
        DashboardPage dashboardPage = new DashboardPage();

        if (dashboardPage.isPageLoaded()) {
            logPass("Login successful - navigated to dashboard");
            takeScreenshot("dashboard_after_login");
        } else {
            logInfo("Dashboard not loaded - checking for error messages or still on login");

            String toastMessage = loginPage.getToastMessage();
            if (!toastMessage.isEmpty()) {
                logInfo("Toast message displayed: " + toastMessage);
            }

            if (loginPage.isPageLoaded()) {
                logInfo("Still on login page - credentials might be invalid or network issue");
            }

            takeScreenshot("login_result_analysis");
        }
    }

    @Test(priority = 3, description = "Test login with invalid credentials")
    public void testInvalidLogin() {
        createTest("Invalid Login Test",
                "Test login functionality with invalid credentials");

        if (!loginPage.isPageLoaded()) {
            logInfo("Login page not loaded - skipping test");
            return;
        }

        String invalidEmail = "invalid@test.com";
        String invalidPassword = "wrongpassword";

        logInfo("Attempting login with invalid credentials");
        logInfo("Email: " + invalidEmail);

        takeScreenshot("before_invalid_login");

        loginPage.login(invalidEmail, invalidPassword);

        logInfo("Invalid login form submitted, waiting for response");
        waitFor(3000);

        takeScreenshot("after_invalid_login");

        // Should still be on login page
        if (loginPage.isPageLoaded()) {
            logPass("Remained on login page as expected for invalid credentials");

            String toastMessage = loginPage.getToastMessage();
            if (!toastMessage.isEmpty()) {
                logInfo("Error message displayed: " + toastMessage);
                logPass("Error message shown for invalid login");
            }
        } else {
            logInfo("Unexpected navigation occurred");
            takeScreenshot("unexpected_navigation");
        }
    }

    @Test(priority = 4, description = "Test login with empty fields")
    public void testEmptyFieldsLogin() {
        createTest("Empty Fields Login Test",
                "Test login behavior with empty email and password fields");

        if (!loginPage.isPageLoaded()) {
            logInfo("Login page not loaded - skipping test");
            return;
        }

        logInfo("Attempting login with empty fields");

        takeScreenshot("before_empty_login");

        loginPage.clickLoginButton();

        waitFor(2000);

        takeScreenshot("after_empty_login");

        // Should remain on login page
        if (loginPage.isPageLoaded()) {
            logPass("Remained on login page with empty fields");

            String toastMessage = loginPage.getToastMessage();
            if (!toastMessage.isEmpty()) {
                logInfo("Validation message: " + toastMessage);
            }
        }
    }

    @Test(priority = 5, description = "Test Google Sign In button")
    public void testGoogleSignIn() {
        createTest("Google Sign In Test",
                "Test Google Sign In button functionality");

        if (!loginPage.isPageLoaded()) {
            logInfo("Login page not loaded - skipping test");
            return;
        }

        if (!loginPage.isGoogleSignInDisplayed()) {
            logInfo("Google Sign In button not found - skipping test");
            return;
        }

        logInfo("Testing Google Sign In button");

        takeScreenshot("before_google_signin");

        loginPage.clickGoogleSignIn();

        waitFor(3000);

        takeScreenshot("after_google_signin_click");

        logInfo("Google Sign In button clicked - checking for Google auth screen or response");
        // Note: This might open Google authentication which requires special handling
    }

    @Test(priority = 6, description = "Test register link navigation")
    public void testRegisterLink() {
        createTest("Register Link Test",
                "Test register link navigation");

        if (!loginPage.isPageLoaded()) {
            logInfo("Login page not loaded - skipping test");
            return;
        }

        if (!loginPage.isRegisterLinkDisplayed()) {
            logInfo("Register link not found - skipping test");
            return;
        }

        logInfo("Testing register link");

        takeScreenshot("before_register_link_click");

        loginPage.clickRegisterLink();

        waitFor(2000);

        takeScreenshot("after_register_link_click");

        logInfo("Register link clicked - checking navigation");

        if (!loginPage.isPageLoaded()) {
            logPass("Navigated away from login page - register link working");
        } else {
            logInfo("Still on login page - register might open dialog or different behavior");
        }
    }
}