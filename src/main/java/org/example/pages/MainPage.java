package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class MainPage extends BasePage {

    private static final By LOGO = By.cssSelector("img[src='/static/images/home/logo.png']");
    private static final By SIGN_UP_LOGIN_BUTTON = By.cssSelector("a[href='/login']");
    private static final By DELETE_ACCOUNT_BUTTON = By.cssSelector("a[href='/delete_account']");
    private static final By CONTACT_US_BUTTON = By.cssSelector("a[href='/contact_us']");
    private static final By PRODUCTS_BUTTON = By.cssSelector("a[href='/products']");
    private static final By CART_BUTTON = By.xpath("(//a[@href='/view_cart'])[1]");
    private static final By ACCOUNT_DELETED = By.xpath("//b[contains(text(),'Account Deleted!')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String url) {
        validateRequiredValue(url);
        logger.info(" =====> Navigating To: {} <===== ", url);
        open(url);
    }

    public boolean isLogoDisplayed() {
        logger.info(" =====> Verifying Automation Exercise Logo Is Displayed <===== ");
        return isDisplayed(LOGO);
    }

    public SignUpLoginPage clickSignUpLoginButton() {
        logger.info(" =====> User Clicks The Sign Up / Login Button <===== ");
        click(SIGN_UP_LOGIN_BUTTON);

        logger.info(" =====> User Navigates To SignUpLoginPage <===== ");
        return new SignUpLoginPage(driver);
    }

    public boolean isSignUpLoginButtonDisplayed() {
        return isDisplayed(SIGN_UP_LOGIN_BUTTON);
    }

    public boolean isDeleteAccountButtonDisplayed() {
        return isDisplayed(DELETE_ACCOUNT_BUTTON);
    }

    public boolean isContactUsButtonDisplayed() {
        return isDisplayed(CONTACT_US_BUTTON);
    }

    public boolean isProductsButtonDisplayed() {
        return isDisplayed(PRODUCTS_BUTTON);
    }

    public boolean isCartButtonDisplayed() {
        return isDisplayed(CART_BUTTON);
    }

    public void clickDeleteAccountButton() {
        logger.info(" =====> User Clicks The Delete Account Button <===== ");
        click(DELETE_ACCOUNT_BUTTON);
    }

    public void clickContactUsButton() {
        logger.info(" =====> User Clicks The Contact Us Button <===== ");
        click(CONTACT_US_BUTTON);
    }

    public void clickProductsButton() {
        logger.info(" =====> User Clicks The Products Button <===== ");
        click(PRODUCTS_BUTTON);
    }

    public void clickCartButton() {
        logger.info(" =====> User Clicks The Cart Button <===== ");
        click(CART_BUTTON);
    }

    private void validateRequiredValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("URL" + " cannot be null or blank.");
        }
    }

    public void verifyTheLoggedInUsername(String userName) {
        By locator = By.xpath("//b[contains(normalize-space(.), '" + userName + "')]");
        verifyTextContent(locator, userName);
    }

    public boolean isAccountDeletedHMessageDisplayed() {
        return isDisplayed(ACCOUNT_DELETED);
    }
}