package com.elektronicare.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class WelcomePage extends BasePage {
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/create_account_button")
    private WebElement createAccountButton;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/sign_in_button")
    private WebElement signInButton;
    
    // Welcome screen elements
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/welcome_text")
    private WebElement welcomeTitle;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/subtitle_text")
    private WebElement subtitleText;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/logo_image")
    private WebElement logoImage;
    
    public WelcomePage() {
        super();
    }
    
    @Override
    public String getPageTitle() {
        return "Welcome";
    }
    
    @Override
    public boolean isPageLoaded() {
        initializeElements();
        return isWelcomePageDisplayed();
    }
    
    public boolean isWelcomePageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(createAccountButton));
            return createAccountButton.isDisplayed() && signInButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isWelcomeTitleDisplayed() {
        try {
            return welcomeTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isSubtitleDisplayed() {
        try {
            return subtitleText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getSubtitleText() {
        try {
            return subtitleText.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isLogoDisplayed() {
        try {
            return logoImage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickCreateAccount() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        createAccountButton.click();
        initializeElements();
    }
    
    public void clickSignIn() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
        initializeElements();
    }
    
    public String getCreateAccountButtonText() {
        try {
            return createAccountButton.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getSignInButtonText() {
        try {
            return signInButton.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean areButtonsEnabled() {
        try {
            return createAccountButton.isEnabled() && signInButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean areButtonsClickable() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isAppTitleDisplayed() {
        return isWelcomeTitleDisplayed();
    }
}