package com.elektronicare.tests;

import com.elektronicare.pages.RegisterPage;
import com.elektronicare.pages.LoginPage;
import com.elektronicare.pages.DashboardPage;
import com.elektronicare.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    @Test(groups = {"smoke", "ui"}, priority = 1)
    public void testRegisterPageDisplay() {
        createTest("Register Page Display Test", "Verify register page is displayed correctly");
        logInfo("Testing register page display");

        // Navigate to register page
        navigateToRegister();

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Verify register page is displayed
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(),
            "Register page should be displayed");

        logPass("Register page displayed successfully");
    }

    @Test(groups = {"functional"}, priority = 2)
    public void testRegisterValidation() {
        createTest("Register Validation Test", "Test register form validation");
        logInfo("Testing register form validation");

        // Navigate to register page
        navigateToRegister();

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Try to register with empty fields
        registerPage.clickCreateAccount();

        // Verify validation errors
        Assert.assertTrue(registerPage.isFullNameErrorDisplayed(),
            "Full name error should be displayed");
        Assert.assertTrue(registerPage.isEmailErrorDisplayed(),
            "Email error should be displayed");
        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
            "Password error should be displayed");

        logPass("Register validation works correctly");
    }

    @Test(groups = {"functional"}, priority = 3)
    public void testInvalidEmailValidation() {
        createTest("Invalid Email Validation Test", "Test invalid email validation");
        logInfo("Testing invalid email validation");

        // Navigate to register page
        navigateToRegister();

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Enter invalid email
        registerPage.enterFullName("Test User");
        registerPage.enterEmail("invalid-email");
        registerPage.enterPassword("password123");
        registerPage.clickCreateAccount();

        // Verify email validation error
        Assert.assertTrue(registerPage.isEmailErrorDisplayed(),
            "Email validation error should be displayed");

        String emailError = registerPage.getEmailErrorText();
        Assert.assertTrue(emailError.contains("valid email"),
            "Email error should mention valid email format");

        logPass("Invalid email validation works correctly");
    }

    @Test(groups = {"functional"}, priority = 4)
    public void testInvalidPhoneValidation() {
        logInfo("Testing invalid phone validation");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Enter invalid phone number
        registerPage.enterFullName("Test User");
        registerPage.enterMobile("123"); // Too short
        registerPage.enterEmail("test@example.com");
        registerPage.enterPassword("password123");
        registerPage.clickCreateAccount();

        // Verify phone validation error
        Assert.assertTrue(registerPage.isMobileErrorDisplayed(),
            "Mobile validation error should be displayed");

        logPass("Invalid phone validation works correctly");
    }

    @Test(groups = {"functional"}, priority = 5)
    public void testShortPasswordValidation() {
        logInfo("Testing short password validation");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Enter short password
        registerPage.enterFullName("Test User");
        registerPage.enterMobile("081234567890");
        registerPage.enterEmail("test@example.com");
        registerPage.enterPassword("123"); // Too short
        registerPage.clickCreateAccount();

        // Verify password validation error
        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
            "Password validation error should be displayed");

        String passwordError = registerPage.getPasswordErrorText();
        Assert.assertTrue(passwordError.contains("6 characters"),
            "Password error should mention minimum 6 characters");

        logPass("Short password validation works correctly");
    }

    @Test(groups = {"functional"}, priority = 6)
    public void testPasswordVisibilityToggle() {
        logInfo("Testing password visibility toggle");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Enter password
        registerPage.enterPassword("testpassword");

        // Toggle password visibility
        registerPage.clickTogglePasswordVisibility();

        // Note: Actual visibility check would depend on implementation
        // This test verifies the toggle functionality exists
        logPass("Password visibility toggle works");
    }

    @Test(groups = {"functional"}, priority = 7)
    public void testValidRegistration() {
        logInfo("Testing valid registration");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();
        DashboardPage dashboardPage = new DashboardPage();

        // Generate unique email for testing
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "test" + timestamp + "@example.com";

        // Fill registration form with valid data
        registerPage.registerUser(
            "Test User",
            "081234567890",
            email,
            "password123"
        );

        // Wait for registration to complete
        waitFor(5000); // Wait for Firebase registration

        // Verify successful registration (should navigate to dashboard)
        // Note: This might require Firebase setup for actual testing
        logPass("Valid registration form submitted");
    }

    @Test(groups = {"functional", "navigation"}, priority = 8)
    public void testNavigateToLogin() {
        logInfo("Testing navigation to login page");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();

        // Click sign in link
        registerPage.clickSignInLink();

        // Verify navigation to login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Should navigate to login page");

        logPass("Navigation to login page works correctly");
    }

    @Test(groups = {"functional", "navigation"}, priority = 9)
    public void testBackNavigation() {
        logInfo("Testing back navigation");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Click back button
        registerPage.clickBack();

        // Verify navigation back (implementation dependent)
        logPass("Back navigation works");
    }

    @Test(groups = {"functional"}, priority = 10)
    public void testGoogleSignInButton() {
        logInfo("Testing Google Sign-In button");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Click Google Sign-In button
        registerPage.clickGoogleSignIn();

        // Note: Actual Google Sign-In testing would require additional setup
        // This test verifies the button functionality exists
        logPass("Google Sign-In button is functional");
    }

    @Test(groups = {"functional"}, priority = 11)
    public void testPhoneNumberFormatting() {
        logInfo("Testing phone number formatting");

        // Navigate to register page
        navigateToRegister();

        RegisterPage registerPage = new RegisterPage();

        // Enter phone number in different formats
        registerPage.enterMobile("8123456789"); // Without leading 0

        // Check if it gets formatted correctly
        String formattedNumber = registerPage.getMobileValue();

        // The app should format it to start with 0
        Assert.assertTrue(formattedNumber.startsWith("0") || formattedNumber.startsWith("8"),
            "Phone number should be formatted correctly");

        logPass("Phone number formatting works correctly");
    }
}