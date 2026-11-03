package org.example.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.core.DriverFactory;
import org.example.pages.AccountCreatedPage;
import org.example.pages.MainPage;
import org.example.pages.SignUpLoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LogoutUserSteps {

    private MainPage mainPage;
    private SignUpLoginPage signUpLoginPage;
    private AccountCreatedPage accountCreatedPage;

    @Then("Click Logout button")
    public void click_logout_button() {
        WebDriver driver = DriverFactory.getDriver();
        accountCreatedPage = new AccountCreatedPage(driver);
        signUpLoginPage = accountCreatedPage.doClickOnLogOutButton();
    }

    @Then("Verify that user is navigated to login page")
    public void verify_that_user_is_navigated_to_login_page() {
        Assert.assertTrue(signUpLoginPage.isLoginToYourFormDisplayed());
        Assert.assertTrue(signUpLoginPage.isNewUserFormDisplayed());
    }
}
