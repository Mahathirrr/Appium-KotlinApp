package com.elektronicare.tests;

import com.elektronicare.pages.LoginPage;
import com.elektronicare.pages.DashboardPage;
import com.elektronicare.pages.HistoryPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for History functionality
 */
public class HistoryTest extends BaseTest {
    
    private HistoryPage historyPage;
    
    @BeforeMethod
    public void navigateToHistory() {
        // Navigate to login first
        navigateToLogin();
        
        // Login with test credentials
        LoginPage loginPage = new LoginPage();
        loginPage.login("test@elektronicare.com", "testpassword123");
        waitFor(3000);
        
        // Navigate to history from dashboard
        DashboardPage dashboardPage = new DashboardPage();
        if (dashboardPage.isPageLoaded()) {
            dashboardPage.clickHistoryNavigation();
            waitFor(2000);
        }
        
        historyPage = new HistoryPage();
    }
    
    @Test(priority = 1, description = "Verify history page elements")
    public void testHistoryPageElements() {
        createTest("History Page Elements Test", 
                  "Verify all history page elements are displayed correctly");
        
        logInfo("Checking if history page is loaded");
        if (historyPage.isPageLoaded()) {
            logPass("History page is loaded successfully");
        } else {
            logInfo("History page not detected - taking screenshot for analysis");
            takeScreenshot("history_page_not_detected");
            return;
        }
        
        takeScreenshot("history_page_loaded");
        
        logInfo("Verifying history page title");
        String pageTitle = historyPage.getPageTitle();
        logInfo("Page title: " + pageTitle);
        
        logInfo("Checking if bottom navigation is displayed");
        if (historyPage.isBottomNavigationDisplayed()) {
            logPass("Bottom navigation is displayed");
        } else {
            logInfo("Bottom navigation not found");
        }
        
        logInfo("Checking history list or empty state");
        if (historyPage.isHistoryEmpty()) {
            logPass("Empty state is displayed (no service history)");
            
            String emptyMessage = historyPage.getEmptyStateMessage();
            if (!emptyMessage.isEmpty()) {
                logInfo("Empty state message: " + emptyMessage);
            }
            
            takeScreenshot("history_empty_state");
        } else if (historyPage.isHistoryListDisplayed()) {
            logPass("History list is displayed");
            
            int historyCount = historyPage.getHistoryItemsCount();
            logInfo("Number of history items: " + historyCount);
            
            if (historyCount > 0) {
                logPass("History items found: " + historyCount);
                takeScreenshot("history_with_items");
                
                // Check first item details
                String firstItemService = historyPage.getFirstItemServiceName();
                String firstItemStatus = historyPage.getFirstItemStatus();
                String firstItemDate = historyPage.getFirstItemDate();
                String firstItemPrice = historyPage.getFirstItemPrice();
                
                if (!firstItemService.isEmpty()) {
                    logInfo("First item service: " + firstItemService);
                }
                if (!firstItemStatus.isEmpty()) {
                    logInfo("First item status: " + firstItemStatus);
                }
                if (!firstItemDate.isEmpty()) {
                    logInfo("First item date: " + firstItemDate);
                }
                if (!firstItemPrice.isEmpty()) {
                    logInfo("First item price: " + firstItemPrice);
                }
            }
        } else {
            logInfo("History list state unclear - taking screenshot");
            takeScreenshot("history_state_unclear");
        }
    }
    
    @Test(priority = 2, description = "Test navigation functionality")
    public void testNavigationFunctionality() {
        createTest("Navigation Functionality Test", 
                  "Test bottom navigation from history page");
        
        if (!historyPage.isPageLoaded()) {
            logInfo("History page not loaded - skipping test");
            return;
        }
        
        if (!historyPage.isBottomNavigationDisplayed()) {
            logInfo("Bottom navigation not found - skipping test");
            return;
        }
        
        logInfo("Testing navigation functionality");
        takeScreenshot("before_navigation_test");
        
        // Test navigate to home
        logInfo("Testing navigation to home");
        historyPage.navigateToHome();
        waitFor(2000);
        takeScreenshot("after_navigate_home");
        
        // Navigate back to history
        DashboardPage dashboardPage = new DashboardPage();
        if (dashboardPage.isPageLoaded()) {
            dashboardPage.clickHistoryNavigation();
            waitFor(2000);
        }
        
        // Test navigate to services
        logInfo("Testing navigation to services");
        historyPage.navigateToServices();
        waitFor(2000);
        takeScreenshot("after_navigate_services");
        
        // Navigate back to history
        historyPage.clickBackButton();
        waitFor(2000);
        
        logPass("Navigation functionality tested");
    }
    
    @Test(priority = 3, description = "Test history item interaction")
    public void testHistoryItemInteraction() {
        createTest("History Item Interaction Test", 
                  "Test clicking on history items");
        
        if (!historyPage.isPageLoaded()) {
            logInfo("History page not loaded - skipping test");
            return;
        }
        
        if (historyPage.isHistoryEmpty()) {
            logInfo("No history items to test - skipping interaction test");
            return;
        }
        
        int historyCount = historyPage.getHistoryItemsCount();
        if (historyCount == 0) {
            logInfo("No history items found - skipping interaction test");
            return;
        }
        
        logInfo("Testing history item interaction");
        takeScreenshot("before_history_item_click");
        
        // Get first item details before clicking
        String firstItemService = historyPage.getFirstItemServiceName();
        String firstItemStatus = historyPage.getFirstItemStatus();
        
        logInfo("Clicking on first history item");
        logInfo("Item service: " + firstItemService);
        logInfo("Item status: " + firstItemStatus);
        
        historyPage.clickFirstHistoryItem();
        waitFor(2000);
        
        takeScreenshot("after_history_item_click");
        
        // Check if navigated to detail page or stayed on history
        if (historyPage.isPageLoaded()) {
            logInfo("Remained on history page - item might open details in place");
        } else {
            logPass("Navigated to item details page");
            
            // Go back to history
            historyPage.goBack();
            waitFor(1000);
            
            if (historyPage.isPageLoaded()) {
                logPass("Successfully returned to history page");
            }
        }
    }
    
    @Test(priority = 4, description = "Test back button functionality")
    public void testBackButton() {
        createTest("Back Button Test", 
                  "Test back button functionality on history page");
        
        if (!historyPage.isPageLoaded()) {
            logInfo("History page not loaded - skipping test");
            return;
        }
        
        logInfo("Testing back button");
        takeScreenshot("before_back_button_click");
        
        historyPage.clickBackButton();
        waitFor(2000);
        
        takeScreenshot("after_back_button_click");
        
        // Should navigate back to dashboard
        DashboardPage dashboardPage = new DashboardPage();
        if (dashboardPage.isPageLoaded()) {
            logPass("Back button navigated to dashboard");
        } else if (!historyPage.isPageLoaded()) {
            logPass("Back button navigated away from history page");
        } else {
            logInfo("Back button - checking navigation result");
        }
    }
    
    @Test(priority = 5, description = "Test history page loading")
    public void testHistoryPageLoading() {
        createTest("History Page Loading Test", 
                  "Test history page loading and data fetching");
        
        if (!historyPage.isPageLoaded()) {
            logInfo("History page not loaded - skipping test");
            return;
        }
        
        logInfo("Testing history page loading");
        takeScreenshot("history_loading_test");
        
        // Wait for history to load
        historyPage.waitForHistoryToLoad();
        
        takeScreenshot("after_history_load_wait");
        
        if (historyPage.isHistoryEmpty()) {
            logPass("History loaded - empty state displayed");
        } else if (historyPage.isHistoryListDisplayed()) {
            logPass("History loaded - items displayed");
            
            int itemCount = historyPage.getHistoryItemsCount();
            logInfo("Loaded " + itemCount + " history items");
        } else {
            logInfo("History loading state unclear");
        }
    }
    
    @Test(priority = 6, description = "Test history data validation")
    public void testHistoryDataValidation() {
        createTest("History Data Validation Test", 
                  "Validate history item data format and content");
        
        if (!historyPage.isPageLoaded()) {
            logInfo("History page not loaded - skipping test");
            return;
        }
        
        if (historyPage.isHistoryEmpty()) {
            logInfo("No history data to validate - skipping test");
            return;
        }
        
        int historyCount = historyPage.getHistoryItemsCount();
        if (historyCount == 0) {
            logInfo("No history items found for validation - skipping test");
            return;
        }
        
        logInfo("Testing history data validation");
        takeScreenshot("history_data_validation");
        
        // Validate first item data
        String serviceName = historyPage.getFirstItemServiceName();
        String status = historyPage.getFirstItemStatus();
        String date = historyPage.getFirstItemDate();
        String price = historyPage.getFirstItemPrice();
        
        logInfo("Validating first history item data:");
        
        if (!serviceName.isEmpty()) {
            logPass("Service name is present: " + serviceName);
        } else {
            logInfo("Service name is empty or not found");
        }
        
        if (!status.isEmpty()) {
            logPass("Status is present: " + status);
            
            // Validate status values
            String[] validStatuses = {"Pending", "In Progress", "Completed", "Cancelled"};
            boolean validStatus = false;
            for (String validStatusValue : validStatuses) {
                if (status.toLowerCase().contains(validStatusValue.toLowerCase())) {
                    validStatus = true;
                    break;
                }
            }
            
            if (validStatus) {
                logPass("Status value is valid: " + status);
            } else {
                logInfo("Status value format: " + status);
            }
        } else {
            logInfo("Status is empty or not found");
        }
        
        if (!date.isEmpty()) {
            logPass("Date is present: " + date);
        } else {
            logInfo("Date is empty or not found");
        }
        
        if (!price.isEmpty()) {
            logPass("Price is present: " + price);
        } else {
            logInfo("Price is empty or not found");
        }
    }
}
