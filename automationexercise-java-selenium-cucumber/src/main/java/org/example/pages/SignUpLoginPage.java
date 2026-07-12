package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class SignUpLoginPage extends BasePage {

    private static final By NEW_USER_SIGN_UP_HEADING = By.xpath("//h2[normalize-space()='New User Signup!']");
    private static final By NAME_INPUT = By.cssSelector("input[data-qa='signup-name']");
    private static final By SIGN_UP_EMAIL_INPUT = By.cssSelector("input[data-qa='signup-email']");
    private static final By SIGN_UP_BUTTON = By.cssSelector("button[data-qa='signup-button']");
    private static final By LOGIN_HEADING = By.xpath("//h2[normalize-space()='Login to your account']");
    private static final By LOGIN_EMAIL_INPUT = By.cssSelector("input[data-qa='login-email']");
    private static final By LOGIN_PASSWORD_INPUT = By.cssSelector("input[data-qa='login-password']");
    private static final By LOGIN_BUTTON = By.cssSelector("button[data-qa='login-button']");
    private static final By SIGN_UP_ERROR_MESSAGE = By.xpath("//p[normalize-space()='Email Address already exist!']");
    private static final By LOGIN_ERROR_MESSAGE = By.xpath("//p[normalize-space()='Your email or password is incorrect!']");

    public SignUpLoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNewUserSignUpHeadingDisplayed() {
        return isDisplayed(NEW_USER_SIGN_UP_HEADING);
    }

    public boolean isLoginHeadingDisplayed() {
        return isDisplayed(LOGIN_HEADING);
    }

    public AccountInformationPage doNewUserSignUp(String user, String email) {
        validateRequiredValue(user, "User name");
        validateRequiredValue(email, "Email");

        logger.info(" =====> User Sends Keys to Name Input: {} <===== ", user);
        type(NAME_INPUT, user);

        logger.info(" =====> User Sends Keys to Sign-Up Email Input: {} <===== ", email);
        type(SIGN_UP_EMAIL_INPUT, email);

        logger.info(" =====> User Clicks The Sign-Up Button <===== ");
        click(SIGN_UP_BUTTON);

        return new AccountInformationPage(driver);
    }

    public MainPage doLogin(String email, String password) {
        validateRequiredValue(email, "Email");
        validateRequiredValue(password, "Password");

        logger.info(" =====> User Sends Keys to Login Email Input: {} <===== ", email);

        type(LOGIN_EMAIL_INPUT, email);

        logger.info(" =====> User Sends Keys to Login Password Input <===== ");

        type(LOGIN_PASSWORD_INPUT, password);

        logger.info(" =====> User Clicks The Login Button <===== ");

        click(LOGIN_BUTTON);

        return new MainPage(driver);
    }

    public void enterSignUpName(String user) {
        validateRequiredValue(user, "User name");

        logger.info(" =====> User Sends Keys to Name Input: {} <===== ", user);

        type(NAME_INPUT, user);
    }

    public void enterSignUpEmail(String email) {
        validateRequiredValue(email, "Email");

        logger.info(" =====> User Sends Keys to Sign-Up Email Input: {} <===== ", email);

        type(SIGN_UP_EMAIL_INPUT, email);
    }

    public AccountInformationPage clickSignUpButton() {
        logger.info(" =====> User Clicks The Sign-Up Button <===== ");

        click(SIGN_UP_BUTTON);

        return new AccountInformationPage(driver);
    }

    public void enterLoginEmail(String email) {
        validateRequiredValue(email, "Email");

        logger.info(" =====> User Sends Keys to Login Email Input: {} <===== ", email);

        type(LOGIN_EMAIL_INPUT, email);
    }

    public void enterLoginPassword(String password) {
        validateRequiredValue(password, "Password");
        logger.info(" =====> User Sends Keys to Login Password Input <===== ");
        type(LOGIN_PASSWORD_INPUT, password);
    }

    public MainPage clickLoginButton() {
        logger.info(" =====> User Clicks The Login Button <===== ");
        click(LOGIN_BUTTON);
        return new MainPage(driver);
    }

    public boolean isSignUpButtonDisplayed() {
        return isDisplayed(SIGN_UP_BUTTON);
    }

    public boolean isSignUpButtonEnabled() {
        return isEnabled(SIGN_UP_BUTTON);
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(LOGIN_BUTTON);
    }

    public boolean isLoginButtonEnabled() {
        return isEnabled(LOGIN_BUTTON);
    }

    public boolean isSignUpErrorMessageDisplayed() {
        return isDisplayed(SIGN_UP_ERROR_MESSAGE);
    }

    public String getSignUpErrorMessage() {
        return getText(SIGN_UP_ERROR_MESSAGE);
    }

    public boolean verifySignUpErrorMessage() {
        return "Email Address already exist!".equals(getSignUpErrorMessage());
    }

    public boolean isLoginErrorMessageDisplayed() {
        return isDisplayed(LOGIN_ERROR_MESSAGE);
    }

    public String getLoginErrorMessage() {
        return getText(LOGIN_ERROR_MESSAGE);
    }

    public boolean verifyLoginErrorMessage() {
        return "Your email or password is incorrect!".equals(getLoginErrorMessage());
    }

    private void validateRequiredValue(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }
}