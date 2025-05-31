package com.elektronicare.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class OnboardingPage extends BasePage {

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/viewPager")
    private WebElement viewPager;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/nextButton")
    private WebElement nextButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/skipButton")
    private WebElement skipButton;

    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/indicatorContainer")
    private WebElement indicatorContainer;

    // Onboarding content elements
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Phone Repairs')]")
    private WebElement phoneRepairsTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Expert repairs for all smartphone brands')]")
    private WebElement phoneRepairsDescription;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Laptop Services')]")
    private WebElement laptopServicesTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Professional laptop repair')]")
    private WebElement laptopServicesDescription;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'TV Repairs')]")
    private WebElement tvRepairsTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Specialized repair for all your TV devices')]")
    private WebElement tvRepairsDescription;

    public OnboardingPage() {
        super();
    }

    @Override
    public String getPageTitle() {
        return "Onboarding";
    }

    @Override
    public boolean isPageLoaded() {
        initializeElements();
        return isOnboardingDisplayed();
    }

    public boolean isOnboardingDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(viewPager));
            return viewPager.isDisplayed() && nextButton.isDisplayed() && skipButton.isDisplayed();
        } catch (Exception e) {
            System.out.println("Onboarding not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isOnboardingPageDisplayed() {
        initializeElements();
        return isOnboardingDisplayed();
    }

    public boolean isPhoneRepairsPageDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(phoneRepairsTitle));
            return phoneRepairsTitle.isDisplayed() && phoneRepairsDescription.isDisplayed();
        } catch (Exception e) {
            System.out.println("Phone repairs page not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isLaptopServicesPageDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(laptopServicesTitle));
            return laptopServicesTitle.isDisplayed() && laptopServicesDescription.isDisplayed();
        } catch (Exception e) {
            System.out.println("Laptop services page not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isTvRepairsPageDisplayed() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(tvRepairsTitle));
            return tvRepairsTitle.isDisplayed() && tvRepairsDescription.isDisplayed();
        } catch (Exception e) {
            System.out.println("TV repairs page not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickNext() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            nextButton.click();
            System.out.println("Clicked next button successfully");
        } catch (Exception e) {
            System.out.println("Failed to click next button: " + e.getMessage());
        }
    }

    public void clickNextAndWaitForLaptopServices() {
        try {
            initializeElements();
            clickNext();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // Wait for previous page to become invisible and next page to be visible
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.invisibilityOf(phoneRepairsTitle),
                    ExpectedConditions.visibilityOf(laptopServicesTitle)));
            System.out.println("Successfully navigated to laptop services page");
        } catch (Exception e) {
            System.out.println("Failed to navigate to laptop services: " + e.getMessage());
        }
    }

    public void clickNextAndWaitForTvRepairs() {
        try {
            initializeElements();
            clickNext();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // Wait for previous page to become invisible and next page to be visible
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.invisibilityOf(laptopServicesTitle),
                    ExpectedConditions.visibilityOf(tvRepairsTitle)));
            System.out.println("Successfully navigated to TV repairs page");
        } catch (Exception e) {
            System.out.println("Failed to navigate to TV repairs: " + e.getMessage());
        }
    }

    public void clickSkip() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(skipButton));
            skipButton.click();
            System.out.println("Clicked skip button successfully");
        } catch (Exception e) {
            System.out.println("Failed to click skip button: " + e.getMessage());
        }
    }

    public String getNextButtonText() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(nextButton));
            return nextButton.getText();
        } catch (Exception e) {
            System.out.println("Failed to get next button text: " + e.getMessage());
            return "";
        }
    }

    public boolean isGetStartedButtonDisplayed() {
        try {
            initializeElements();
            String buttonText = getNextButtonText();
            return "Get Started".equals(buttonText) || "GET STARTED".equals(buttonText);
        } catch (Exception e) {
            System.out.println("Error checking Get Started button: " + e.getMessage());
            return false;
        }
    }

    public void swipeToNextPage() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(viewPager));

            // Swipe left on viewPager to go to next page
            int startX = viewPager.getSize().getWidth() * 3 / 4;
            int endX = viewPager.getSize().getWidth() / 4;
            int y = viewPager.getSize().getHeight() / 2;

            performSwipe(startX, y, endX, y);

            // Wait for swipe animation to complete
            WebDriverWait swipeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            swipeWait.until(driver -> {
                try {
                    return viewPager.isDisplayed();
                } catch (Exception e) {
                    return true;
                }
            });
            System.out.println("Swiped to next page successfully");
        } catch (Exception e) {
            System.out.println("Error swiping to next page: " + e.getMessage());
        }
    }

    public void swipeToPreviousPage() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(viewPager));

            // Swipe right on viewPager to go to previous page
            int startX = viewPager.getSize().getWidth() / 4;
            int endX = viewPager.getSize().getWidth() * 3 / 4;
            int y = viewPager.getSize().getHeight() / 2;

            performSwipe(startX, y, endX, y);

            // Wait for swipe animation to complete
            WebDriverWait swipeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            swipeWait.until(driver -> {
                try {
                    return viewPager.isDisplayed();
                } catch (Exception e) {
                    return true;
                }
            });
            System.out.println("Swiped to previous page successfully");
        } catch (Exception e) {
            System.out.println("Error swiping to previous page: " + e.getMessage());
        }
    }

    public int getIndicatorCount() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(indicatorContainer));
            return indicatorContainer.findElements(org.openqa.selenium.By.xpath(".//*")).size();
        } catch (Exception e) {
            System.out.println("Error getting indicator count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Skip onboarding by clicking skip button
     */
    public void skipOnboarding() {
        try {
            initializeElements();
            clickSkip();
            System.out.println("Onboarding skipped successfully");
        } catch (Exception e) {
            System.out.println("Error skipping onboarding: " + e.getMessage());
        }
    }

    /**
     * Complete onboarding by going through all pages
     */
    public void completeOnboarding() {
        try {
            initializeElements();
            System.out.println("Starting onboarding completion process");

            int maxAttempts = 5; // Prevent infinite loop
            int attempts = 0;

            // Navigate to last page and click Get Started
            while (!isGetStartedButtonDisplayed() && attempts < maxAttempts) {
                attempts++;
                System.out.println("Attempt " + attempts + ": Clicking next to navigate");
                clickNext();

                // Wait for navigation to complete before checking next page
                WebDriverWait navigationWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                navigationWait.until(driver -> {
                    try {
                        initializeElements();
                        return viewPager.isDisplayed();
                    } catch (Exception e) {
                        return true;
                    }
                });

                // Small delay to allow UI to settle
                Thread.sleep(1000);
            }

            if (isGetStartedButtonDisplayed()) {
                System.out.println("Found Get Started button, clicking to complete onboarding");
                clickNext(); // Click "Get Started"
                System.out.println("Onboarding completed successfully");
            } else {
                System.out.println("Could not find Get Started button after " + maxAttempts + " attempts");
            }
        } catch (Exception e) {
            System.out.println("Error completing onboarding: " + e.getMessage());
        }
    }

    /**
     * Wait for onboarding page to be fully loaded
     */
    public void waitForOnboardingToLoad() {
        try {
            initializeElements();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.visibilityOf(viewPager),
                    ExpectedConditions.visibilityOf(nextButton),
                    ExpectedConditions.visibilityOf(skipButton)));
            System.out.println("Onboarding page loaded successfully");
        } catch (Exception e) {
            System.out.println("Timeout waiting for onboarding to load: " + e.getMessage());
        }
    }

    /**
     * Get current page indicator (useful for debugging)
     */
    public int getCurrentPageIndicator() {
        try {
            initializeElements();
            // This would need specific implementation based on your indicator design
            // For now, return a default value
            return 0;
        } catch (Exception e) {
            System.out.println("Error getting current page indicator: " + e.getMessage());
            return -1;
        }
    }
}