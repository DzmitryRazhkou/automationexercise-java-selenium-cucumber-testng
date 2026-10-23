package org.example.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.DriverFactory;
import org.example.pages.AccountCreatedPage;
import org.example.pages.MainPage;
import org.example.pages.SignUpLoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginUserCorrectCredentialsSteps {

    private MainPage mainPage;
    private SignUpLoginPage signUpLoginPage;
    private AccountCreatedPage accountCreatedPage;

    @Then("the Login to your account section should be visible")
    public void the_login_to_your_account_section_should_be_visible() {

        WebDriver driver = DriverFactory.getDriver();
        signUpLoginPage = new SignUpLoginPage(driver);
        Assert.assertTrue(signUpLoginPage.isNewUserSignUpHeadingDisplayed());
    }

    @When("the user enters email {string} and password {string}")
    public void the_user_enters_email_and_password(String email, String psw) {
        accountCreatedPage = signUpLoginPage.doLoginAccountCreated(email, psw);
    }

    @Then("the user should be logged in successfully as {string}")
    public void the_user_should_be_logged_in_successfully_as(String fullName) {
        Assert.assertTrue(accountCreatedPage.verifyTheLoggedInUsername(fullName));
    }
}
