package com.elektronicare.tests;

import com.elektronicare.pages.ServicesPage;
import com.elektronicare.pages.DashboardPage;
import com.elektronicare.pages.LoginPage;
import com.elektronicare.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class ServicesTest extends BaseTest {

    @Test(groups = { "smoke", "ui" }, priority = 1)
    public void testServicesPageDisplay() {
        logInfo("Testing services page display");

        // Navigate to login and then to services via dashboard
        navigateToLogin();

        // Login with test credentials
        LoginPage loginPage = new LoginPage();
        loginPage.login("test@elektronicare.com", "testpassword123");
        waitFor(3000);

        // Navigate to services from dashboard
        DashboardPage dashboardPage = new DashboardPage();
        if (dashboardPage.isPageLoaded()) {
            dashboardPage.clickServicesNavigation();
            waitFor(2000);
        }

        ServicesPage servicesPage = new ServicesPage();

        // Verify services page is displayed
        Assert.assertTrue(servicesPage.isServicesPageDisplayed(),
                "Services page should be displayed");

        // Log service details for debugging
        servicesPage.logServiceDetails();

        logPass("Services page displayed successfully");
    }

    @Test(groups = { "functional" }, priority = 2)
    public void testServicesLoading() {
        logInfo("Testing services loading from Firebase");

        ServicesPage servicesPage = new ServicesPage();

        // Wait for Firebase services to load properly
        servicesPage.waitForServicesToLoad();

        // Verify services are displayed or no services message is shown
        boolean servicesDisplayed = servicesPage.areServicesDisplayed();
        boolean noServicesMessage = servicesPage.isNoServicesMessageDisplayed();

        Assert.assertTrue(servicesDisplayed || noServicesMessage,
                "Either services should be displayed or no services message should be shown");

        if (servicesDisplayed) {
            logInfo("Services loaded successfully from Firebase");
            servicesPage.logServiceDetails();
        } else {
            logInfo("No services available - showing empty state");
        }

        logPass("Services loading works correctly");
    }

    @Test(groups = { "functional", "navigation" }, priority = 7)
    public void testBottomNavigation() {
        logInfo("Testing bottom navigation from services page");

        ServicesPage servicesPage = new ServicesPage();
        DashboardPage dashboardPage = new DashboardPage();

        // Test navigation to home
        servicesPage.navigateToHome();
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Should navigate to dashboard via bottom navigation");

        // Navigate back to services
        dashboardPage.navigateToServices();
        Assert.assertTrue(servicesPage.isServicesPageDisplayed(),
                "Should navigate back to services");

        logPass("Bottom navigation works correctly");
    }
}