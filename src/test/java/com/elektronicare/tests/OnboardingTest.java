package com.elektronicare.tests;

import com.elektronicare.pages.OnboardingPage;
import com.elektronicare.pages.WelcomePage;
import com.elektronicare.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OnboardingTest extends BaseTest {

    @Test(groups = { "smoke", "ui" }, priority = 1)
    public void testOnboardingPageDisplay() {
        createTest("Onboarding Page Display Test", "Verify onboarding page is displayed correctly");
        logInfo("Testing onboarding page display");

        OnboardingPage onboardingPage = new OnboardingPage();

        // Verify onboarding page is displayed
        Assert.assertTrue(onboardingPage.isOnboardingDisplayed(),
                "Onboarding page should be displayed");

        // Verify first page (Phone Repairs) is displayed
        Assert.assertTrue(onboardingPage.isPhoneRepairsPageDisplayed(),
                "Phone Repairs page should be displayed first");

        logPass("Onboarding page displayed successfully");
    }

    @Test(groups = { "functional" }, priority = 2)
    public void testOnboardingNavigation() {
        createTest("Onboarding Navigation Test", "Test navigation through onboarding pages");
        logInfo("Testing onboarding navigation");

        OnboardingPage onboardingPage = new OnboardingPage();

        // Navigate through onboarding pages using Next button
        onboardingPage.clickNextAndWaitForLaptopServices();
        Assert.assertTrue(onboardingPage.isLaptopServicesPageDisplayed(),
                "Should navigate to Laptop Services page");

        onboardingPage.clickNextAndWaitForTvRepairs();
        Assert.assertTrue(onboardingPage.isTvRepairsPageDisplayed(),
                "Should navigate to TV Repairs page");

        // Verify Get Started button appears on last page
        Assert.assertTrue(onboardingPage.isGetStartedButtonDisplayed(),
                "Get Started button should appear on last page");

        logPass("Onboarding navigation works correctly");
    }

    @Test(groups = { "functional" }, priority = 3)
    public void testOnboardingSkip() {
        createTest("Onboarding Skip Test", "Test skip functionality in onboarding");
        logInfo("Testing onboarding skip functionality");

        OnboardingPage onboardingPage = new OnboardingPage();
        WelcomePage welcomePage = new WelcomePage();

        // Skip onboarding
        onboardingPage.clickSkip();

        // Verify navigation to welcome page
        Assert.assertTrue(welcomePage.isWelcomePageDisplayed(),
                "Should navigate to welcome page after skipping onboarding");

        logPass("Onboarding skip functionality works correctly");
    }

    @Test(groups = { "functional" }, priority = 4)
    public void testOnboardingComplete() {
        createTest("Onboarding Complete Test", "Test completing onboarding flow");
        logInfo("Testing onboarding completion");

        OnboardingPage onboardingPage = new OnboardingPage();
        WelcomePage welcomePage = new WelcomePage();

        // Navigate to last page
        onboardingPage.clickNext();
        onboardingPage.clickNext();

        // Complete onboarding
        onboardingPage.clickNext(); // This should be "Get Started" button

        // Verify navigation to welcome page
        Assert.assertTrue(welcomePage.isWelcomePageDisplayed(),
                "Should navigate to welcome page after completing onboarding");

        logPass("Onboarding completion works correctly");
    }
}