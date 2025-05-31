package com.elektronicare.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class ServicesPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Our Services']")
    private WebElement servicesTitle;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/back_button")
    private WebElement backButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/services_recycler_view")
    private WebElement servicesRecyclerView;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/no_services_view")
    private WebElement noServicesView;

    // Service items in RecyclerView - using proper selectors for MaterialCardView
    // items
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.elektronicarebeta1:id/services_recycler_view']//com.google.android.material.card.MaterialCardView")
    private List<WebElement> serviceCards;

    // Service names in cards (from Firebase)
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.elektronicarebeta1:id/services_recycler_view']//android.widget.TextView[@resource-id='com.example.elektronicarebeta1:id/service_name']")
    private List<WebElement> serviceNames;

    // Service categories in cards
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.elektronicarebeta1:id/services_recycler_view']//android.widget.TextView[@resource-id='com.example.elektronicarebeta1:id/service_category']")
    private List<WebElement> serviceCategories;

    // Service descriptions in cards
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.elektronicarebeta1:id/services_recycler_view']//android.widget.TextView[@resource-id='com.example.elektronicarebeta1:id/service_description']")
    private List<WebElement> serviceDescriptions;

    // Service prices in cards
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.elektronicarebeta1:id/services_recycler_view']//android.widget.TextView[@resource-id='com.example.elektronicarebeta1:id/service_price']")
    private List<WebElement> servicePrices;

    // Service icons in cards
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.example.elektronicarebeta1:id/services_recycler_view']//android.widget.ImageView[@resource-id='com.example.elektronicarebeta1:id/service_icon']")
    private List<WebElement> serviceIcons;

    // Bottom navigation
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_home")
    private WebElement homeNavigation;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_history")
    private WebElement historyNavigation;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_services")
    private WebElement servicesNavigation;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nav_profile")
    private WebElement profileNavigation;

    public ServicesPage() {
        super();
    }

    @Override
    public String getPageTitle() {
        return "Services";
    }

    @Override
    public boolean isPageLoaded() {
        initializeElements();
        return isServicesPageDisplayed();
    }

    public boolean isServicesPageDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(servicesTitle));
            return servicesTitle.isDisplayed();
        } catch (Exception e) {
            System.out.println("Services page not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickBackButton() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(backButton));
            backButton.click();
            System.out.println("Clicked back button successfully");
        } catch (Exception e) {
            System.out.println("Failed to click back button: " + e.getMessage());
        }
    }

    /**
     * Wait for Firebase services to load with proper timeout
     */
    public void waitForServicesToLoad() {
        try {
            initializeElements();
            System.out.println("Waiting for Firebase services to load...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Wait for either services to appear or no services message
            wait.until(driver -> {
                try {
                    initializeElements();
                    return areServicesDisplayed() || isNoServicesMessageDisplayed();
                } catch (Exception e) {
                    return false;
                }
            });

            // Additional wait for Firebase data to fully load if services are present
            if (areServicesDisplayed()) {
                WebDriverWait firebaseWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                firebaseWait.until(driver -> {
                    try {
                        initializeElements();
                        // Wait for service cards to be populated with data
                        return serviceCards.size() > 0 &&
                                serviceNames.size() > 0 &&
                                !serviceNames.isEmpty() &&
                                !serviceNames.get(0).getText().trim().isEmpty();
                    } catch (Exception e) {
                        return false;
                    }
                });
            }
            System.out.println("Services loading completed");
        } catch (Exception e) {
            System.out.println("Timeout waiting for services to load: " + e.getMessage());
        }
    }

    /**
     * Select service by name (from Firebase data)
     */
    public void selectService(String serviceName) {
        try {
            System.out.println("Attempting to select service: " + serviceName);
            initializeElements();
            waitForServicesToLoad();

            // Find service by name in service cards
            for (WebElement nameElement : serviceNames) {
                if (nameElement.getText().toLowerCase().contains(serviceName.toLowerCase())) {
                    // Click the parent card
                    WebElement card = nameElement.findElement(org.openqa.selenium.By
                            .xpath("./ancestor::com.google.android.material.card.MaterialCardView"));
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                    wait.until(ExpectedConditions.elementToBeClickable(card));
                    card.click();
                    System.out.println("Successfully selected service: " + serviceName);
                    return;
                }
            }
            System.out.println("Service not found: " + serviceName);
        } catch (Exception e) {
            System.out.println("Failed to select service " + serviceName + ": " + e.getMessage());
        }
    }

    /**
     * Select service by category (phone, laptop, tv, etc.)
     */
    public void selectServiceByCategory(String category) {
        try {
            System.out.println("Attempting to select service by category: " + category);
            initializeElements();
            waitForServicesToLoad();

            // Find service by category
            for (WebElement categoryElement : serviceCategories) {
                if (categoryElement.getText().toLowerCase().contains(category.toLowerCase())) {
                    // Click the parent card
                    WebElement card = categoryElement.findElement(org.openqa.selenium.By
                            .xpath("./ancestor::com.google.android.material.card.MaterialCardView"));
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                    wait.until(ExpectedConditions.elementToBeClickable(card));
                    card.click();
                    System.out.println("Successfully selected service by category: " + category);
                    return;
                }
            }
            System.out.println("Service category not found: " + category);
        } catch (Exception e) {
            System.out.println("Failed to select service by category " + category + ": " + e.getMessage());
        }
    }

    public void selectPhoneRepairService() {
        initializeElements();
        selectServiceByCategory("phone");
    }

    public void selectLaptopRepairService() {
        initializeElements();
        selectServiceByCategory("laptop");
    }

    public void selectTvRepairService() {
        initializeElements();
        selectServiceByCategory("tv");
    }

    public boolean isPhoneRepairServiceDisplayed() {
        initializeElements();
        return isServiceCategoryDisplayed("phone");
    }

    public boolean isLaptopRepairServiceDisplayed() {
        initializeElements();
        return isServiceCategoryDisplayed("laptop");
    }

    public boolean isTvRepairServiceDisplayed() {
        initializeElements();
        return isServiceCategoryDisplayed("tv");
    }

    /**
     * Check if service with specific name is displayed
     */
    public boolean isServiceDisplayed(String serviceName) {
        try {
            initializeElements();
            waitForServicesToLoad();
            for (WebElement nameElement : serviceNames) {
                if (nameElement.getText().toLowerCase().contains(serviceName.toLowerCase())) {
                    return nameElement.isDisplayed();
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error checking if service is displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if service with specific category is displayed
     */
    public boolean isServiceCategoryDisplayed(String category) {
        try {
            initializeElements();
            waitForServicesToLoad();
            for (WebElement categoryElement : serviceCategories) {
                if (categoryElement.getText().toLowerCase().contains(category.toLowerCase())) {
                    return categoryElement.isDisplayed();
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error checking if service category is displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean areServicesDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(servicesRecyclerView));
            return servicesRecyclerView.isDisplayed() && getServicesCount() > 0;
        } catch (Exception e) {
            System.out.println("Services not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isNoServicesMessageDisplayed() {
        try {
            initializeElements();
            return noServicesView.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToHome() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(homeNavigation));
            homeNavigation.click();
            System.out.println("Navigated to home successfully");
        } catch (Exception e) {
            System.out.println("Failed to navigate to home: " + e.getMessage());
        }
    }

    public void navigateToHistory() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(historyNavigation));
            historyNavigation.click();
            System.out.println("Navigated to history successfully");
        } catch (Exception e) {
            System.out.println("Failed to navigate to history: " + e.getMessage());
        }
    }

    public void navigateToProfile() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(profileNavigation));
            profileNavigation.click();
            System.out.println("Navigated to profile successfully");
        } catch (Exception e) {
            System.out.println("Failed to navigate to profile: " + e.getMessage());
        }
    }

    /**
     * Get count of service cards from RecyclerView
     */
    public int getServicesCount() {
        try {
            initializeElements();
            waitForServicesToLoad();
            return serviceCards != null ? serviceCards.size() : 0;
        } catch (Exception e) {
            System.out.println("Error getting services count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get all service names from Firebase data
     */
    public List<String> getServiceNames() {
        try {
            initializeElements();
            waitForServicesToLoad();
            return serviceNames.stream()
                    .map(WebElement::getText)
                    .filter(text -> text != null && !text.trim().isEmpty())
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error getting service names: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    /**
     * Get all service categories from Firebase data
     */
    public List<String> getServiceCategories() {
        try {
            initializeElements();
            waitForServicesToLoad();
            return serviceCategories.stream()
                    .map(WebElement::getText)
                    .filter(text -> text != null && !text.trim().isEmpty())
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error getting service categories: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    /**
     * Get all service prices from Firebase data
     */
    public List<String> getServicePrices() {
        try {
            initializeElements();
            waitForServicesToLoad();
            return servicePrices.stream()
                    .map(WebElement::getText)
                    .filter(text -> text != null && text.contains("Rp"))
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error getting service prices: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public boolean areServicePricesDisplayed() {
        try {
            initializeElements();
            waitForServicesToLoad();
            return servicePrices != null && !servicePrices.isEmpty() &&
                    servicePrices.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            System.out.println("Error checking service prices: " + e.getMessage());
            return false;
        }
    }

    /**
     * Scroll to find a specific service in RecyclerView
     */
    public void scrollToService(String serviceName) {
        try {
            System.out.println("Scrolling to find service: " + serviceName);
            initializeElements();

            // Try to find the service first
            if (isServiceDisplayed(serviceName)) {
                System.out.println("Service already visible: " + serviceName);
                return;
            }

            // Scroll down to find the service
            for (int i = 0; i < 5; i++) {
                scrollDown();
                // Wait for scroll animation to complete and content to load
                WebDriverWait scrollWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                scrollWait.until(driver -> {
                    try {
                        initializeElements();
                        return serviceCards.size() > 0; // Wait for content to be stable
                    } catch (Exception e) {
                        return true; // Continue if there's an issue
                    }
                });

                if (isServiceDisplayed(serviceName)) {
                    System.out.println("Found service after scrolling: " + serviceName);
                    return;
                }
            }

            System.out.println("Service not found after scrolling: " + serviceName);
        } catch (Exception e) {
            System.out.println("Error scrolling to service: " + e.getMessage());
        }
    }

    /**
     * Get detailed service information for debugging
     */
    public void logServiceDetails() {
        try {
            initializeElements();
            waitForServicesToLoad();
            System.out.println("=== SERVICE DETAILS ===");
            System.out.println("Total service cards: " + serviceCards.size());
            System.out.println("Service names: " + getServiceNames());
            System.out.println("Service categories: " + getServiceCategories());
            System.out.println("Service prices: " + getServicePrices());
            System.out.println("======================");
        } catch (Exception e) {
            System.out.println("Error logging service details: " + e.getMessage());
        }
    }

    /**
     * Check if services recycler view is visible
     */
    public boolean isServicesRecyclerViewDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(servicesRecyclerView));
            return servicesRecyclerView.isDisplayed();
        } catch (Exception e) {
            System.out.println("Services recycler view not displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if bottom navigation is visible
     */
    public boolean isBottomNavigationDisplayed() {
        try {
            initializeElements();
            return homeNavigation.isDisplayed() &&
                    historyNavigation.isDisplayed() &&
                    servicesNavigation.isDisplayed() &&
                    profileNavigation.isDisplayed();
        } catch (Exception e) {
            System.out.println("Bottom navigation not displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Wait for services page to be fully loaded with all elements
     */
    public void waitForServicesPageToLoad() {
        try {
            initializeElements();
            System.out.println("Waiting for services page to fully load...");

            // Wait for main title
            WebDriverWait titleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            titleWait.until(ExpectedConditions.visibilityOf(servicesTitle));

            // Wait for recycler view
            WebDriverWait recyclerWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            recyclerWait.until(ExpectedConditions.visibilityOf(servicesRecyclerView));

            // Wait for services to load
            waitForServicesToLoad();

            System.out.println("Services page fully loaded");
        } catch (Exception e) {
            System.out.println("Timeout waiting for services page to load: " + e.getMessage());
        }
    }

    /**
     * Refresh services page by pulling down
     */
    public void refreshServices() {
        try {
            initializeElements();
            System.out.println("Refreshing services...");

            // Perform pull-to-refresh gesture
            int startY = servicesRecyclerView.getSize().getHeight() / 4;
            int endY = servicesRecyclerView.getSize().getHeight() * 3 / 4;
            int x = servicesRecyclerView.getSize().getWidth() / 2;

            performSwipe(x, startY, x, endY);

            // Wait for refresh to complete
            waitForServicesToLoad();

            System.out.println("Services refreshed successfully");
        } catch (Exception e) {
            System.out.println("Error refreshing services: " + e.getMessage());
        }
    }

    /**
     * Check if a specific service card is clickable
     */
    public boolean isServiceCardClickable(int index) {
        try {
            initializeElements();
            waitForServicesToLoad();

            if (index >= 0 && index < serviceCards.size()) {
                WebElement card = serviceCards.get(index);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.elementToBeClickable(card));
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Service card not clickable at index " + index + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Click service card by index
     */
    public void clickServiceCard(int index) {
        try {
            initializeElements();
            waitForServicesToLoad();

            if (index >= 0 && index < serviceCards.size()) {
                WebElement card = serviceCards.get(index);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                wait.until(ExpectedConditions.elementToBeClickable(card));
                card.click();
                System.out.println("Clicked service card at index: " + index);
            } else {
                System.out.println("Invalid service card index: " + index);
            }
        } catch (Exception e) {
            System.out.println("Failed to click service card at index " + index + ": " + e.getMessage());
        }
    }

    /**
     * Get service name by index
     */
    public String getServiceNameByIndex(int index) {
        try {
            initializeElements();
            waitForServicesToLoad();

            if (index >= 0 && index < serviceNames.size()) {
                return serviceNames.get(index).getText();
            }
            return "";
        } catch (Exception e) {
            System.out.println("Error getting service name at index " + index + ": " + e.getMessage());
            return "";
        }
    }

    /**
     * Get service category by index
     */
    public String getServiceCategoryByIndex(int index) {
        try {
            initializeElements();
            waitForServicesToLoad();

            if (index >= 0 && index < serviceCategories.size()) {
                return serviceCategories.get(index).getText();
            }
            return "";
        } catch (Exception e) {
            System.out.println("Error getting service category at index " + index + ": " + e.getMessage());
            return "";
        }
    }

    /**
     * Check if services are loading (show loading state)
     */
    public boolean areServicesLoading() {
        try {
            initializeElements();
            // This would depend on your loading indicator implementation
            // For now, we'll check if recycler view is visible but empty
            return servicesRecyclerView.isDisplayed() && serviceCards.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
