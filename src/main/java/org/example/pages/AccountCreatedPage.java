package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class AccountCreatedPage extends BasePage {

    private static final By ACCOUNT_CREATED_HEADING = By.cssSelector("h2[data-qa='account-created']");
    private static final By CONTINUE_BUTTON = By.xpath("//a[@data-qa='continue-button']");
    private static final By LOGGED_IN_AS_USERNAME = By.xpath("//b[contains(text(), 'Dzmitry Razhkou')]");
    private static final By LOGOUT_BUTTON = By.xpath("//a[@href='/logout']");

    public AccountCreatedPage(WebDriver driver) {
        super(driver);
    }

    private WebElement getContinueButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON));
    }

    private WebElement getLogOutButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
    }

    public boolean isAccountCreatedHeadingDisplayed() {
        return isDisplayed(ACCOUNT_CREATED_HEADING);
    }

    public String getAccountCreatedHeading() {
        return getText(ACCOUNT_CREATED_HEADING);
    }

    public MainPage clickOnContinueButton() {
        logger.info(" =====> User Clicks The 'Continue' Button <===== ");
        click(CONTINUE_BUTTON);
        return new MainPage(driver);
    }

    public boolean verifyTheLoggedInUsername(String userName) {
        By locator = By.xpath("//b[contains(normalize-space(.), '" + userName + "')]");
        return verifyTextContent(locator, userName);
    }

    public SignUpLoginPage doClickOnLogOutButton() {

        logger.info(" =====> User Clicks On The 'Log Out' Button <===== ");
        getLogOutButton().click();

        logger.info(" =====> User Navigates On The 'SignUpLoginPage' <===== ");
        return new SignUpLoginPage(driver);
    }

}