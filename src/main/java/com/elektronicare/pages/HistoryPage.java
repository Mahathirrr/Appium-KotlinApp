package com.elektronicare.pages;

import com.elektronicare.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Page Object for History Screen
 */
public class HistoryPage extends BasePage {

    // Header elements
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/back_button")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Repair History']")
    private WebElement titleText;

    // History list
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/repairs_container")
    private WebElement repairsContainer;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/scrollView")
    private WebElement scrollView;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[contains(@resource-id, 'repair_item')]")
    private List<WebElement> historyItems;

    // Empty state
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/no_repairs_view")
    private WebElement noRepairsView;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='No Repair History']")
    private WebElement noRepairsText;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"You haven't made any repair requests yet.\"]")
    private WebElement noRepairsDescription;

    // History item elements (for first item)
    @AndroidFindBy(xpath = "(//android.widget.TextView[contains(@resource-id, 'serviceNameText')])[1]")
    private WebElement firstItemServiceName;

    @AndroidFindBy(xpath = "(//android.widget.TextView[contains(@resource-id, 'statusText')])[1]")
    private WebElement firstItemStatus;

    @AndroidFindBy(xpath = "(//android.widget.TextView[contains(@resource-id, 'dateText')])[1]")
    private WebElement firstItemDate;

    @AndroidFindBy(xpath = "(//android.widget.TextView[contains(@resource-id, 'priceText')])[1]")
    private WebElement firstItemPrice;

    // Bottom navigation
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_home")
    private WebElement navHome;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_history")
    private WebElement navHistory;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_services")
    private WebElement navServices;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_profile")
    private WebElement navProfile;

    /**
     * Check if history page is loaded
     */
    @Override
    public boolean isPageLoaded() {
        initializeElements();
        try {
            return TestUtils.isElementDisplayed(titleText) &&
                    (TestUtils.isElementDisplayed(repairsContainer) ||
                            TestUtils.isElementDisplayed(noRepairsView));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get page title
     */
    @Override
    public String getPageTitle() {
        initializeElements();
        if (TestUtils.isElementDisplayed(titleText)) {
            return TestUtils.getElementText(titleText);
        }
        return "Service History";
    }

    /**
     * Click back button
     */
    public void clickBackButton() {
        initializeElements();
        TestUtils.safeClick(backButton);
    }

    /**
     * Get number of history items
     */
    public int getHistoryItemsCount() {
        initializeElements();
        if (TestUtils.isElementDisplayed(repairsContainer)) {
            return historyItems.size();
        }
        return 0;
    }

    /**
     * Check if history list is empty
     */
    public boolean isHistoryEmpty() {
        initializeElements();
        return TestUtils.isElementDisplayed(noRepairsView);
    }

    /**
     * Get empty state message
     */
    public String getEmptyStateMessage() {
        initializeElements();
        if (TestUtils.isElementDisplayed(noRepairsText)) {
            return TestUtils.getElementText(noRepairsText);
        }
        return "";
    }

    /**
     * Navigate to home
     */
    public void navigateToHome() {
        initializeElements();
        TestUtils.safeClick(navHome);
    }

    /**
     * Navigate to services
     */
    public void navigateToServices() {
        initializeElements();
        TestUtils.safeClick(navServices);
    }

    /**
     * Navigate to profile
     */
    public void navigateToProfile() {
        initializeElements();
        TestUtils.safeClick(navProfile);
    }

    /**
     * Click on first history item
     */
    public void clickFirstHistoryItem() {
        initializeElements();
        if (getHistoryItemsCount() > 0) {
            TestUtils.safeClick(historyItems.get(0));
        }
    }

    /**
     * Get first item service name
     */
    public String getFirstItemServiceName() {
        initializeElements();
        if (TestUtils.isElementDisplayed(firstItemServiceName)) {
            return TestUtils.getElementText(firstItemServiceName);
        }
        return "";
    }

    /**
     * Get first item status
     */
    public String getFirstItemStatus() {
        initializeElements();
        if (TestUtils.isElementDisplayed(firstItemStatus)) {
            return TestUtils.getElementText(firstItemStatus);
        }
        return "";
    }

    /**
     * Get first item date
     */
    public String getFirstItemDate() {
        initializeElements();
        if (TestUtils.isElementDisplayed(firstItemDate)) {
            return TestUtils.getElementText(firstItemDate);
        }
        return "";
    }

    /**
     * Get first item price
     */
    public String getFirstItemPrice() {
        initializeElements();
        if (TestUtils.isElementDisplayed(firstItemPrice)) {
            return TestUtils.getElementText(firstItemPrice);
        }
        return "";
    }

    /**
     * Check if history list is displayed
     */
    public boolean isHistoryListDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(repairsContainer);
    }

    /**
     * Check if bottom navigation is displayed
     */
    public boolean isBottomNavigationDisplayed() {
        initializeElements();
        return TestUtils.isElementDisplayed(navHome) &&
                TestUtils.isElementDisplayed(navHistory) &&
                TestUtils.isElementDisplayed(navServices) &&
                TestUtils.isElementDisplayed(navProfile);
    }

    /**
     * Scroll down to load more history items
     */
    public void scrollToLoadMore() {
        initializeElements();
        if (TestUtils.isElementDisplayed(scrollView)) {
            scrollDown();
            waitFor(1000);
        }
    }

    /**
     * Refresh history list by pulling down
     */
    public void refreshHistoryList() {
        initializeElements();
        if (TestUtils.isElementDisplayed(scrollView)) {
            // Perform pull-to-refresh gesture
            scrollUp();
            waitFor(2000);
        }
    }

    /**
     * Wait for history to load
     */
    public void waitForHistoryToLoad() {
        initializeElements();
        waitFor(2000);

        // Wait for either history items or empty state to appear
        int maxWaitTime = 5000; // 5 seconds max
        int waitInterval = 500; // Check every 500ms
        int totalWaitTime = 0;

        while (!TestUtils.isElementDisplayed(repairsContainer) &&
                !TestUtils.isElementDisplayed(noRepairsView) &&
                totalWaitTime < maxWaitTime) {
            waitFor(waitInterval);
            totalWaitTime += waitInterval;
        }
    }
}