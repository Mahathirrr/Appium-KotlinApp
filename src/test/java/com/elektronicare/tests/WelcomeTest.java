package com.elektronicare.tests;

import com.elektronicare.pages.WelcomePage;
import com.elektronicare.pages.RegisterPage;
import com.elektronicare.pages.LoginPage;
import com.elektronicare.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WelcomeTest extends BaseTest {
    
    @Test(groups = {"smoke", "ui"}, priority = 1)
    public void testWelcomePageDisplay() {
        createTest("Welcome Page Display Test", "Verify welcome page is displayed correctly");
        logInfo("Testing welcome page display");
        
        // Navigate to welcome page
        navigateToWelcome();
        
        // Navigate to welcome page
        navigateToWelcome();
        
        WelcomePage welcomePage = new WelcomePage();
        
        // Verify welcome page is displayed
        Assert.assertTrue(welcomePage.isWelcomePageDisplayed(), 
            "Welcome page should be displayed");
        
        // Verify UI elements
        Assert.assertTrue(welcomePage.isLogoDisplayed(), 
            "Logo should be displayed");
        
        Assert.assertTrue(welcomePage.areButtonsEnabled(), 
            "Buttons should be enabled");
        
        logPass("Welcome page displayed successfully");
    }
    
    @Test(groups = {"ui"}, priority = 2)
    public void testWelcomePageElements() {
        createTest("Welcome Page Elements Test", "Test welcome page UI elements");
        logInfo("Testing welcome page UI elements");
        
        // Navigate to welcome page
        navigateToWelcome();
        
        WelcomePage welcomePage = new WelcomePage();
        
        // Verify button texts
        String createAccountText = welcomePage.getCreateAccountButtonText();
        String signInText = welcomePage.getSignInButtonText();
        
        Assert.assertFalse(createAccountText.isEmpty(), 
            "Create Account button should have text");
        Assert.assertFalse(signInText.isEmpty(), 
            "Sign In button should have text");
        
        // Verify buttons are clickable
        Assert.assertTrue(welcomePage.areButtonsClickable(), 
            "Buttons should be clickable");
        
        logPass("Welcome page elements are correct");
    }
    
    @Test(groups = {"functional", "navigation"}, priority = 3)
    public void testNavigateToRegister() {
        createTest("Navigate to Register Test", "Test navigation to register page");
        logInfo("Testing navigation to register page");
        
        // Navigate to welcome page
        navigateToWelcome();
        
        WelcomePage welcomePage = new WelcomePage();
        RegisterPage registerPage = new RegisterPage();
        
        // Click Create Account button
        welcomePage.clickCreateAccount();
        
        // Verify navigation to register page
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), 
            "Should navigate to register page");
        
        logPass("Navigation to register page works correctly");
    }
    
    @Test(groups = {"functional", "navigation"}, priority = 4)
    public void testNavigateToLogin() {
        createTest("Navigate to Login Test", "Test navigation to login page");
        logInfo("Testing navigation to login page");
        
        // Navigate to welcome page
        navigateToWelcome();
        
        WelcomePage welcomePage = new WelcomePage();
        LoginPage loginPage = new LoginPage();
        
        // Click Sign In button
        welcomePage.clickSignIn();
        
        // Verify navigation to login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
            "Should navigate to login page");
        
        logPass("Navigation to login page works correctly");
    }
    
    @Test(groups = {"ui"}, priority = 5)
    public void testWelcomePageBranding() {
        createTest("Welcome Page Branding Test", "Test welcome page branding elements");
        logInfo("Testing welcome page branding elements");
        
        // Navigate to welcome page
        navigateToWelcome();
        
        WelcomePage welcomePage = new WelcomePage();
        
        // Verify branding elements are displayed
        Assert.assertTrue(welcomePage.isSubtitleDisplayed(), 
            "Subtitle should be displayed");
        
        Assert.assertTrue(welcomePage.isWelcomeTitleDisplayed(), 
            "Welcome title should be displayed");
        
        logPass("Welcome page branding elements are correct");
    }
}