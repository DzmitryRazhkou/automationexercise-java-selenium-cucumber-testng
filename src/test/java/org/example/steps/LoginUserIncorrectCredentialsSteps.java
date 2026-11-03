package org.example.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.DriverFactory;
import org.example.pages.AccountCreatedPage;
import org.example.pages.MainPage;
import org.example.pages.SignUpLoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginUserIncorrectCredentialsSteps {

    private MainPage mainPage;
    private SignUpLoginPage signUpLoginPage;
    private AccountCreatedPage accountCreatedPage;

    @When("the user enters incorrect email {string} and password {string}")
    public void the_user_enters_incorrect_email_and_password(String username, String password) {

        WebDriver driver = DriverFactory.getDriver();
        signUpLoginPage = new SignUpLoginPage(driver);
        signUpLoginPage.doLoginAccountCreated(username, password);
    }

    @Then("Verify error {string} is visible")
    public void verify_error_is_visible(String string) {
        Assert.assertTrue(signUpLoginPage.verifyErrorMsg());
    }
}
