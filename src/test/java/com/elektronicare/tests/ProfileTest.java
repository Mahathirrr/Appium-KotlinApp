package com.elektronicare.tests;

import com.elektronicare.tests.BaseTest;
import com.elektronicare.pages.DashboardPage;
import com.elektronicare.pages.ProfilePage;
import com.elektronicare.pages.WelcomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Profile functionality
 */
public class ProfileTest extends BaseTest {
    
    private ProfilePage profilePage;
    private DashboardPage dashboardPage;
    private WelcomePage welcomePage;
    
    @Test(priority = 1, description = "Test profile page navigation")
    public void testProfilePageNavigation() {
        createTest("Profile Page Navigation Test", "Verify user can navigate to profile page");
        
        profilePage = new ProfilePage();
        dashboardPage = new DashboardPage();
        
        // Navigate to profile page
        dashboardPage.clickProfileNavigation();
        
        // Verify profile page is displayed
        Assert.assertTrue(profilePage.isProfilePageDisplayed(), 
            "Profile page should be displayed");
        
        logPass("Successfully navigated to profile page");
    }
    
    @Test(priority = 2, description = "Test profile elements visibility")
    public void testProfileElementsVisibility() {
        createTest("Profile Elements Visibility Test", "Verify all profile elements are visible");
        
        profilePage = new ProfilePage();
        
        // Verify profile elements are displayed
        Assert.assertTrue(profilePage.isLogoutButtonDisplayed(), 
            "Logout button should be displayed");
        
        Assert.assertTrue(profilePage.isProfileImageDisplayed(), 
            "Profile image should be displayed");
        
        logPass("All profile elements are visible");
    }
    
    @Test(priority = 3, description = "Test logout functionality")
    public void testLogoutFunctionality() {
        createTest("Logout Functionality Test", "Verify user can logout successfully");
        
        profilePage = new ProfilePage();
        welcomePage = new WelcomePage();
        
        // Click logout button
        profilePage.clickLogout();
        
        // Verify user is redirected to welcome page
        Assert.assertTrue(welcomePage.isWelcomePageDisplayed(), 
            "User should be redirected to welcome page after logout");
        
        logPass("Logout functionality works correctly");
    }
}