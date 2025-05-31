package com.elektronicare.stepdefinitions;

import com.elektronicare.pages.WelcomePage;
import com.elektronicare.pages.RegisterPage;
import com.elektronicare.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class WelcomeSteps {
    
    private WelcomePage welcomePage;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    
    public WelcomeSteps() {
        this.welcomePage = new WelcomePage();
        this.registerPage = new RegisterPage();
        this.loginPage = new LoginPage();
    }
    
    @Given("I am on the welcome screen")
    public void i_am_on_the_welcome_screen() {
        Assert.assertTrue(welcomePage.isWelcomePageDisplayed(), 
            "Should be on welcome screen");
    }
    
    @Then("I should see the welcome page")
    public void i_should_see_the_welcome_page() {
        Assert.assertTrue(welcomePage.isWelcomePageDisplayed(), 
            "Welcome page should be displayed");
    }
    
    @Then("I should see the app logo")
    public void i_should_see_the_app_logo() {
        Assert.assertTrue(welcomePage.isLogoDisplayed(), 
            "App logo should be displayed");
    }
    
    @Then("I should see the create account button")
    public void i_should_see_the_create_account_button() {
        String buttonText = welcomePage.getCreateAccountButtonText();
        Assert.assertFalse(buttonText.isEmpty(), 
            "Create account button should be visible");
    }
    
    @Then("I should see the sign in button")
    public void i_should_see_the_sign_in_button() {
        String buttonText = welcomePage.getSignInButtonText();
        Assert.assertFalse(buttonText.isEmpty(), 
            "Sign in button should be visible");
    }
    
    @Then("the create account button should have text")
    public void the_create_account_button_should_have_text() {
        String buttonText = welcomePage.getCreateAccountButtonText();
        Assert.assertFalse(buttonText.isEmpty(), 
            "Create account button should have text");
    }
    
    @Then("the sign in button should have text")
    public void the_sign_in_button_should_have_text() {
        String buttonText = welcomePage.getSignInButtonText();
        Assert.assertFalse(buttonText.isEmpty(), 
            "Sign in button should have text");
    }
    
    @Then("both buttons should be enabled")
    public void both_buttons_should_be_enabled() {
        Assert.assertTrue(welcomePage.areButtonsEnabled(), 
            "Both buttons should be enabled");
    }
    
    @Then("both buttons should be clickable")
    public void both_buttons_should_be_clickable() {
        Assert.assertTrue(welcomePage.areButtonsClickable(), 
            "Both buttons should be clickable");
    }
    
    @When("I click the create account button")
    public void i_click_the_create_account_button() {
        welcomePage.clickCreateAccount();
    }
    
    @When("I click the sign in button")
    public void i_click_the_sign_in_button() {
        welcomePage.clickSignIn();
    }
    
    @Then("I should be navigated to the register page")
    public void i_should_be_navigated_to_the_register_page() {
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), 
            "Should be navigated to register page");
    }
    
    @Then("I should be navigated to the login page")
    public void i_should_be_navigated_to_the_login_page() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
            "Should be navigated to login page");
    }
    
    @Then("I should see the app title")
    public void i_should_see_the_app_title() {
        Assert.assertTrue(welcomePage.isAppTitleDisplayed(), 
            "App title should be displayed");
    }
    
    @Then("I should see the welcome title")
    public void i_should_see_the_welcome_title() {
        Assert.assertTrue(welcomePage.isWelcomeTitleDisplayed(), 
            "Welcome title should be displayed");
    }
    
    @Then("I should see the logo")
    public void i_should_see_the_logo() {
        Assert.assertTrue(welcomePage.isLogoDisplayed(), 
            "Logo should be displayed");
    }
}