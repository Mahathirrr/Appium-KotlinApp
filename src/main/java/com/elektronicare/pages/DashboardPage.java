package com.elektronicare.pages;

import com.elektronicare.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Page Object for Dashboard Screen
 */
public class DashboardPage extends BasePage {

    // Header elements
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/welcome_text")
    private WebElement welcomeText;

    // Service categories (using xpath since they are in LinearLayouts)
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Phones']")
    private WebElement phoneServiceCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Laptops']")
    private WebElement laptopServiceCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='TV']")
    private WebElement tvServiceCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Printers']")
    private WebElement printerServiceCard;

    // Recent repairs section
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/view_all_recent")
    private WebElement viewAllRecentButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/repair_card_1")
    private WebElement repairCard1;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/repair_card_2")
    private WebElement repairCard2;

    // Recent services section
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Recent Repairs']")
    private WebElement recentRepairsTitle;

    // Bottom navigation
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_home")
    private WebElement homeNavButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_history")
    private WebElement historyNavButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_services")
    private WebElement servicesNavButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_profile")
    private WebElement profileNavButton;

    // Service items in recent services
    @AndroidFindBy(className = "androidx.recyclerview.widget.RecyclerView")
    private List<WebElement> serviceItems;

    /**
     * Check if dashboard page is loaded
     */
    @Override
    public boolean isPageLoaded() {
        initializeElements();
        try {
            return TestUtils.isElementDisplayed(welcomeText) ||
                    TestUtils.isElementDisplayed(laptopServiceCard) ||
                    TestUtils.isElementDisplayed(phoneServiceCard);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if dashboard is displayed (alias for isPageLoaded)
     */
    public boolean isDashboardDisplayed() {
        initializeElements();
        return isPageLoaded();
    }

    /**
     * Navigate to services page
     */
    public void navigateToServices() {
        initializeElements();
        TestUtils.safeClick(servicesNavButton);
    }

    /**
     * Get page title
     */
    @Override
    public String getPageTitle() {
        return "Dashboard";
    }

    /**
     * Get welcome message
     */
    public String getWelcomeText() {
        initializeElements();
        if (TestUtils.isElementDisplayed(welcomeText)) {
            return TestUtils.getElementText(welcomeText);
        }
        return "";
    }

    /**
     * Click laptop service card
     */
    public void clickLaptopService() {
        initializeElements();
        TestUtils.safeClick(laptopServiceCard);
    }

    /**
     * Click phone service card
     */
    public void clickPhoneService() {
        initializeElements();
        TestUtils.safeClick(phoneServiceCard);
    }

    /**
     * Click TV service card
     */
    public void clickTVService() {
        initializeElements();
        TestUtils.safeClick(tvServiceCard);
    }

    /**
     * Click printer service card
     */
    public void clickPrinterService() {
        initializeElements();
        TestUtils.safeClick(printerServiceCard);
    }

    /**
     * Click view all recent repairs button
     */
    public void clickViewAllRecent() {
        initializeElements();
        TestUtils.safeClick(viewAllRecentButton);
    }

    /**
     * Click first repair card
     */
    public void clickRepairCard1() {
        initializeElements();
        TestUtils.safeClick(repairCard1);
    }

    /**
     * Click second repair card
     */
    public void clickRepairCard2() {
        initializeElements();
        TestUtils.safeClick(repairCard2);
    }

    /**
     * Navigate to home (current page)
     */
    public void clickHomeNavigation() {
        initializeElements();
        TestUtils.safeClick(homeNavButton);
    }

    /**
     * Navigate to history page
     */
    public void clickHistoryNavigation() {
        initializeElements();
        TestUtils.safeClick(historyNavButton);
    }

    /**
     * Navigate to services page
     */
    public void clickServicesNavigation() {
        initializeElements();
        TestUtils.safeClick(servicesNavButton);
    }

    /**
     * Navigate to profile page
     */
    public void clickProfileNavigation() {
        initializeElements();
        TestUtils.safeClick(profileNavButton);
    }

    /**
     * Check if service cards are displayed
     */
    public boolean areServiceCardsDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(laptopServiceCard) &&
                TestUtils.isElementDisplayed(phoneServiceCard) &&
                TestUtils.isElementDisplayed(tvServiceCard) &&
                TestUtils.isElementDisplayed(printerServiceCard);
    }

    /**
     * Check if recent repair cards are displayed
     */
    public boolean areRepairCardsDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(repairCard1) ||
                TestUtils.isElementDisplayed(repairCard2);
    }

    /**
     * Check if bottom navigation is displayed
     */
    public boolean isBottomNavigationDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(homeNavButton) &&
                TestUtils.isElementDisplayed(historyNavButton) &&
                TestUtils.isElementDisplayed(servicesNavButton) &&
                TestUtils.isElementDisplayed(profileNavButton);
    }

    /**
     * Check if recent repairs section is displayed
     */
    public boolean isRecentRepairsSectionDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(recentRepairsTitle);
    }

    /**
     * Check if view all recent button is displayed
     */
    public boolean isViewAllRecentDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(viewAllRecentButton);
    }

    /**
     * Check if welcome text is displayed
     */
    public boolean isWelcomeTextDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(welcomeText);
    }

    // Methods that match the real implementation
    public void clickViewHistory() {
        initializeElements();
        clickHistoryNavigation();
        initializeElements();
    }

    public boolean isProfileImageDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(profileNavButton);
    }

    public void clickProfileImage() {
        initializeElements();
        clickProfileNavigation();
        initializeElements();
    }

    // These methods don't exist in real implementation - redirect to services
    public void clickBookService() {
        initializeElements();
        clickServicesNavigation();
        initializeElements();
    }

}