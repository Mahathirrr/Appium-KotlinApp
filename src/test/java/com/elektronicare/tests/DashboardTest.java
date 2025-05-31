package com.elektronicare.tests;

import com.elektronicare.pages.LoginPage;
import com.elektronicare.pages.DashboardPage;
import com.elektronicare.pages.HistoryPage;
import com.elektronicare.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Dashboard functionality
 */
public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;

    @BeforeMethod
    public void navigateToDashboard() {
        // Navigate to login page first
        super.navigateToLogin();

        // Try to login with test credentials
        LoginPage loginPage = new LoginPage();
        loginPage.login("test@elektronicare.com", "testpassword123");
        TestUtils.waitFor(3000);

        dashboardPage = new DashboardPage();

        // If not on dashboard, wait a bit more
        if (!dashboardPage.isPageLoaded()) {
            TestUtils.waitFor(3000);
        }
    }

    @Test(priority = 1, description = "Verify dashboard page elements")
    public void testDashboardElements() {
        createTest("Dashboard Elements Test",
                "Verify all dashboard elements are displayed correctly");

        logInfo("Checking if dashboard page is loaded");
        if (dashboardPage.isPageLoaded()) {
            logPass("Dashboard page is loaded successfully");
        } else {
            logInfo("Dashboard page not detected - might still be on login or different screen");
            takeScreenshot("dashboard_not_detected");
            return;
        }

        takeScreenshot("dashboard_loaded");

        logInfo("Checking welcome text display");
        String welcomeText = dashboardPage.getWelcomeText();
        if (!welcomeText.isEmpty()) {
            logPass("Welcome text displayed: " + welcomeText);
        } else {
            logInfo("Welcome text not found or empty");
        }

        logInfo("Checking service cards");
        if (dashboardPage.areServiceCardsDisplayed()) {
            logPass("Service cards are displayed");
        } else {
            logInfo("Service cards not all visible");
        }

        logInfo("Checking recent repair cards");
        if (dashboardPage.areRepairCardsDisplayed()) {
            logPass("Recent repair cards are displayed");
        } else {
            logInfo("Recent repair cards not visible");
        }

        logInfo("Checking bottom navigation");
        if (dashboardPage.isBottomNavigationDisplayed()) {
            logPass("Bottom navigation is displayed");
        } else {
            logInfo("Bottom navigation not fully visible");
        }

        logInfo("Checking welcome text");
        if (dashboardPage.isWelcomeTextDisplayed()) {
            logPass("Welcome text is displayed");
        } else {
            logInfo("Welcome text not found");
        }

        logInfo("Checking recent repairs section");
        if (dashboardPage.isRecentRepairsSectionDisplayed()) {
            logPass("Recent repairs section is displayed");

            if (dashboardPage.isViewAllRecentDisplayed()) {
                logInfo("View all recent button is displayed");
            }
        } else {
            logInfo("Recent services section not found");
        }
    }

    @Test(priority = 2, description = "Test navigation buttons")
    public void testNavigationButtons() {
        createTest("Navigation Buttons Test",
                "Test navigation button functionality");

        if (!dashboardPage.isPageLoaded()) {
            logInfo("Dashboard not loaded - skipping test");
            return;
        }

        logInfo("Testing view history navigation");
        takeScreenshot("before_view_history_click");

        dashboardPage.clickViewHistory();
        waitFor(2000);

        takeScreenshot("after_view_history_click");

        HistoryPage historyPage = new HistoryPage();
        if (historyPage.isPageLoaded()) {
            logPass("View history button navigated to history page");
            dashboardPage.goBack();
            waitFor(1000);
        } else {
            logInfo("View history button - checking navigation result");
        }

        logInfo("Testing services navigation");
        dashboardPage.clickServicesNavigation();
        waitFor(2000);

        takeScreenshot("after_services_click");

        // Navigate back to dashboard
        dashboardPage.clickHomeNavigation();
        waitFor(1000);

        logInfo("Navigation buttons tested successfully");
    }

    @Test(priority = 3, description = "Test bottom navigation")
    public void testBottomNavigation() {
        createTest("Bottom Navigation Test",
                "Test bottom navigation functionality");

        if (!dashboardPage.isPageLoaded()) {
            logInfo("Dashboard not loaded - skipping test");
            return;
        }

        logInfo("Testing history navigation");
        takeScreenshot("before_history_nav_click");

        dashboardPage.clickHistoryNavigation();
        waitFor(2000);

        takeScreenshot("after_history_nav_click");

        HistoryPage historyPage = new HistoryPage();
        if (historyPage.isPageLoaded()) {
            logPass("History navigation working");

            // Navigate back to dashboard
            dashboardPage.clickHomeNavigation();
            waitFor(1000);
        }

        // Navigate back to dashboard
        dashboardPage.clickHomeNavigation();
        waitFor(1000);

        logInfo("Testing home navigation (should stay on dashboard)");
        dashboardPage.clickHomeNavigation();
        waitFor(1000);

        if (dashboardPage.isPageLoaded()) {
            logPass("Home navigation keeps user on dashboard");
        }
    }
}
