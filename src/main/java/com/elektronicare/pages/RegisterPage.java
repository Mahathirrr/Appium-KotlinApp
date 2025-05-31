package com.elektronicare.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class RegisterPage extends BasePage {
    
    // Input fields
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/edit_full_name")
    private WebElement fullNameField;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/edit_mobile")
    private WebElement mobileField;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/edit_email")
    private WebElement emailField;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/edit_password")
    private WebElement passwordField;
    
    // Error messages
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/fullname_error")
    private WebElement fullNameError;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/mobile_error")
    private WebElement mobileError;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/email_error")
    private WebElement emailError;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/password_error")
    private WebElement passwordError;
    
    // Buttons
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/btn_create_account")
    private WebElement createAccountButton;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/toggle_password_visibility")
    private WebElement togglePasswordVisibility;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/back_button")
    private WebElement backButton;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/link_sign_in")
    private WebElement signInLink;
    
    @AndroidFindBy(id = "com.example.elektronicarebeta1:id/btn_google_signin")
    private WebElement googleSignInButton;
    
    // Page title
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Create Account') or contains(@text, 'Register')]")
    private WebElement pageTitle;
    
    public RegisterPage() {
        super();
    }
    
    @Override
    public String getPageTitle() {
        return "Register";
    }
    
    @Override
    public boolean isPageLoaded() {
        initializeElements();
        return isRegisterPageDisplayed();
    }
    
    public boolean isRegisterPageDisplayed() {
        initializeElements();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(fullNameField));
            return fullNameField.isDisplayed() && emailField.isDisplayed() && 
                   passwordField.isDisplayed() && createAccountButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void enterFullName(String fullName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(fullNameField));
        fullNameField.clear();
        fullNameField.sendKeys(fullName);
        initializeElements();
    }
    
    public void enterMobile(String mobile) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(mobileField));
        mobileField.clear();
        mobileField.sendKeys(mobile);
        initializeElements();
    }
    
    public void enterEmail(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
        initializeElements();
    }
    
    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
        initializeElements();
    }
    
    public void clickCreateAccount() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        createAccountButton.click();
        initializeElements();
    }
    
    public void clickTogglePasswordVisibility() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(togglePasswordVisibility));
        togglePasswordVisibility.click();
        initializeElements();
    }
    
    public void clickBack() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(backButton));
        backButton.click();
        initializeElements();
    }
    
    public void clickSignInLink() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        signInLink.click();
        initializeElements();
    }
    
    public void clickGoogleSignIn() {
        initializeElements();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(googleSignInButton));
        googleSignInButton.click();
        initializeElements();
    }
    
    public void registerUser(String fullName, String mobile, String email, String password) {
        enterFullName(fullName);
        enterMobile(mobile);
        enterEmail(email);
        enterPassword(password);
        clickCreateAccount();
        initializeElements();
    }
    
    public boolean isFullNameErrorDisplayed() {
        initializeElements();
        try {
            return fullNameError.isDisplayed() && !fullNameError.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isMobileErrorDisplayed() {
        initializeElements();
        try {
            return mobileError.isDisplayed() && !mobileError.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isEmailErrorDisplayed() {
        initializeElements();
        try {
            return emailError.isDisplayed() && !emailError.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPasswordErrorDisplayed() {
        initializeElements();
        try {
            return passwordError.isDisplayed() && !passwordError.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getFullNameErrorText() {
        try {
            return fullNameError.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getMobileErrorText() {
        try {
            return mobileError.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getEmailErrorText() {
        try {
            return emailError.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getPasswordErrorText() {
        try {
            return passwordError.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean areAllFieldsEmpty() {
        try {
            return fullNameField.getText().isEmpty() && 
                   mobileField.getText().isEmpty() &&
                   emailField.getText().isEmpty() && 
                   passwordField.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPasswordVisible() {
        try {
            // Check if password field shows actual characters instead of dots
            String inputType = passwordField.getAttribute("password");
            return "false".equals(inputType);
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getFullNameValue() {
        try {
            return fullNameField.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getMobileValue() {
        try {
            return mobileField.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getEmailValue() {
        try {
            return emailField.getText();
        } catch (Exception e) {
            return "";
        }
    }
}