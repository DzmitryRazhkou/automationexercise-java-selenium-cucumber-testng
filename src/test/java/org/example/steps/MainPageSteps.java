package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.ConfigReader;
import org.example.core.DriverFactory;
import org.example.data.CustomerDataFactory;
import org.example.data.CustomerInfo;
import org.example.pages.AccountCreatedPage;
import org.example.pages.AccountInformationPage;
import org.example.pages.MainPage;
import org.example.pages.SignUpLoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.FileNotFoundException;

public class MainPageSteps {

    private final CustomerInfo customer;
    private MainPage mainPage;
    private SignUpLoginPage signUpLoginPage;
    private AccountInformationPage accountInformationPage;
    private AccountCreatedPage accountCreatedPage;
    private String password;

    public MainPageSteps() {
        this.customer = CustomerDataFactory.generateCustomer();
    }

    private String getName() {
        return customer.getFullName();
    }

    private String getFirstName() {
        return customer.getFirstName();
    }

    private String getLastName() {
        return customer.getLastName();
    }

    private String getEmail() {
        return customer.getEmail();
    }

    private String getCompany() {
        return customer.getBookName();
    }

    private String getAddressOne() {
        return customer.getAddress1();
    }

    private String getAddressTwo() {
        return customer.getAddress2();
    }

    private String getState() {
        return customer.getState();
    }

    private String getCity() {
        return customer.getCity();
    }

    private String getZipCode() {
        return customer.getZipCode();
    }

    private String getPhoneNumber() {
        return customer.getPhoneNumber();
    }

    @Given("the user navigates to URL")
    public void the_user_navigates_to_url() {
        WebDriver driver = DriverFactory.getDriver();
        mainPage = new MainPage(driver);
        mainPage.navigateTo(ConfigReader.getBaseUrl());
    }

    @Then("the home page should be displayed successfully")
    public void the_home_page_should_be_displayed_successfully() {
        Assert.assertTrue(mainPage.isLogoDisplayed(), "The Automation Exercise logo is not displayed.");
    }

    @When("the user clicks the Signup \\/ Login button")
    public void the_user_clicks_the_signup_login_button() {
        signUpLoginPage = mainPage.clickSignUpLoginButton();
    }

    @Then("the New User Signup! section should be visible")
    public void the_new_user_signup_section_should_be_visible() {
        Assert.assertTrue(signUpLoginPage.isNewUserSignUpHeadingDisplayed());
    }

    @When("the user signs up as a new user")
    public void the_user_signs_up_as_a_new_user() {
        accountInformationPage = signUpLoginPage.doNewUserSignUp(getName(), getEmail());
    }

    @Then("the ENTER ACCOUNT INFORMATION heading should be visible")
    public void the_enter_account_information_heading_should_be_visible() {
        Assert.assertTrue(accountInformationPage.isEnterAccountInformationDisplayed());
    }

    @When("the user enters valid account information")
    public void the_user_enters_valid_account_information() {
        password = accountInformationPage.generatePassword(10);
        accountInformationPage.selectRandomGender();
        accountInformationPage.enterPassword(password);
        accountInformationPage.doSelectDOB();
    }

    @When("the user subscribes to the newsletter and partner offers")
    public void the_user_subscribes_to_the_newsletter_and_partner_offers() {
        accountInformationPage.selectNewsletterCheckbox();
        accountInformationPage.selectSpecialOffersCheckbox();
    }

    @When("the user enters valid address information")
    public void the_user_enters_valid_address_information() {
        accountInformationPage.enterAddressInformation(getFirstName(), getLastName(), getCompany(), getAddressOne(), getAddressTwo(), getState(), getCity(), getZipCode(), getPhoneNumber());
    }

    @When("the user clicks the Create Account button")
    public void the_user_clicks_the_create_account_button() {
        accountCreatedPage = accountInformationPage.clickCreateAccountButton();
    }

    @Then("the ACCOUNT CREATED! message should be visible")
    public void the_account_created_message_should_be_visible() {
        Assert.assertTrue(accountCreatedPage.isAccountCreatedHeadingDisplayed());
    }

    @When("the user clicks the Continue button")
    public void the_user_clicks_the_continue_button() {
        mainPage = accountCreatedPage.clickOnContinueButton();
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        mainPage.verifyTheLoggedInUsername(getName());
    }

    @When("the user clicks the Delete Account\" button")
    public void the_user_clicks_the_delete_account_button() {
        mainPage.clickDeleteAccountButton();
    }

    @Then("the ACCOUNT DELETED! message should be visible")
    public void the_account_deleted_message_should_be_visible() {
        Assert.assertTrue(mainPage.isAccountDeletedHMessageDisplayed());
    }

}
