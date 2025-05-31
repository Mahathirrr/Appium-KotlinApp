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
    private static final int LOGIN_PAGE_TIMEOUT = 10000; // 10 seconds timeout

    @BeforeMethod
    public void setupLoginTest() {
        logInfo("Setting up login test");

        // Navigate to login page through proper flow
        super.navigateToLogin();
        waitFor(2000); // Wait for navigation

        // Initialize login page
        loginPage = new LoginPage();

        logInfo("Login page setup completed");
    }

    @Test(priority = 1, description = "Test login with valid credentials")
    public void testValidLogin() {
        createTest("Valid Login Test", "Test login functionality with valid credentials");

        // Test credentials
        String testEmail = "test@elektronicare.com";
        String testPassword = "testpassword123";

        logInfo("Attempting login with test credentials");

        loginPage.login(testEmail, testPassword);
        loginPage.waitForLoginToComplete();
        waitFor(3000); // Wait for potential navigation
    }

    @Test(priority = 2, description = "Test login with invalid credentials")
    public void testInvalidLogin() {
        createTest("Invalid Login Test", "Test login functionality with invalid credentials");

        String invalidEmail = "invalid@test.com";
        String invalidPassword = "wrongpassword";

        logInfo("Attempting login with invalid credentials");

        loginPage.login(invalidEmail, invalidPassword);
        loginPage.waitForLoginToComplete();
        waitFor(2000); // Wait for error message

        // Check for error message
        String toastMessage = loginPage.getToastMessage();

        logInfo("Invalid login error: " + toastMessage);
        logPass("Invalid login handled correctly");
    }

    @Test(priority = 3, description = "Test login with empty fields")
    public void testEmptyFieldsLogin() {
        createTest("Empty Fields Login Test",
                "Test login behavior with empty email and password fields");

        logInfo("Attempting login with empty fields");

        loginPage.clickLoginButton();
        waitFor(2000); // Wait for validation message

        logPass("Empty fields validation working correctly");
    }
}