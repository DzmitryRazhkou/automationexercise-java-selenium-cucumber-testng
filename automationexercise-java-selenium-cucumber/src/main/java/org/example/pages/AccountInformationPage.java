package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class AccountInformationPage extends BasePage {

    private static final By ENTER_ACCOUNT_INFORMATION_HEADING = By.xpath("//h2[normalize-space()='Enter Account Information']");
    private static final By MR_RADIO_BUTTON = By.cssSelector("input#id_gender1");
    private static final By MS_RADIO_BUTTON = By.cssSelector("input#id_gender2");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By DAY_DOB_DROPDOWN = By.id("days");
    private static final By MONTH_DOB_DROPDOWN = By.id("months");
    private static final By YEAR_DOB_DROPDOWN = By.id("years");
    private static final By NEWSLETTER_CHECKBOX = By.id("newsletter");
    private static final By SPECIAL_OFFERS_CHECKBOX = By.id("optin");
    private static final By FIRST_NAME_INPUT = By.id("first_name");
    private static final By LAST_NAME_INPUT = By.id("last_name");
    private static final By COMPANY_INPUT = By.id("company");
    private static final By ADDRESS_FIRST_LINE_INPUT = By.id("address1");
    private static final By ADDRESS_SECOND_LINE_INPUT = By.id("address2");
    private static final By COUNTRY_DROPDOWN = By.cssSelector("select[data-qa='country']");
    private static final By STATE_INPUT = By.id("state");
    private static final By CITY_INPUT = By.id("city");
    private static final By ZIP_CODE_INPUT = By.id("zipcode");
    private static final By MOBILE_NUMBER_INPUT = By.id("mobile_number");
    private static final By CREATE_ACCOUNT_BUTTON = By.cssSelector("button[data-qa='create-account']");
    private static final List<String> MONTHS = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    public AccountInformationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEnterAccountInformationDisplayed() {
        return isDisplayed(ENTER_ACCOUNT_INFORMATION_HEADING);
    }

    public void selectRandomGender() {
        boolean selectMr = ThreadLocalRandom.current().nextBoolean();

        if (selectMr) {
            logger.info(" =====> User Selects The 'Mr.' Title <===== ");
            click(MR_RADIO_BUTTON);
        } else {
            logger.info(" =====> User Selects The 'Mrs.' Title <===== ");
            click(MS_RADIO_BUTTON);
        }
    }

    public void selectMrGender() {
        logger.info(" =====> User Selects The 'Mr.' Title <===== ");
        click(MR_RADIO_BUTTON);
    }

    public void selectMsGender() {
        logger.info(" =====> User Selects The 'Mrs.' Title <===== ");
        click(MS_RADIO_BUTTON);
    }

    public void enterPassword(String password) {

        validateRequiredValue(password, "Password");
        logger.info(" =====> User Enters The Account Password <===== ");
        type(PASSWORD_INPUT, password);
    }

    public void selectRandomDateOfBirth() {

        int day = ThreadLocalRandom.current().nextInt(1, 29);
        String month = MONTHS.get(ThreadLocalRandom.current().nextInt(MONTHS.size()));

        int currentYear = Year.now().getValue();
        int year = ThreadLocalRandom.current().nextInt(currentYear - 65, currentYear - 18);

        logger.info(" =====> User Selects Date Of Birth: {} {} {} <===== ", day, month, year);
        selectByVisibleText(DAY_DOB_DROPDOWN, String.valueOf(day));
        selectByVisibleText(MONTH_DOB_DROPDOWN, month);
        selectByVisibleText(YEAR_DOB_DROPDOWN, String.valueOf(year));
    }

    public void doSelectDOB() {

        Random random = new Random();
        String[] years = generateYear();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        String day = String.valueOf(random.nextInt(28) + 1);
        String month = months[random.nextInt(months.length)];
        String year = years[random.nextInt(years.length)];

        // ----- DAY -----
        logger.info("User chooses DAY: {}", day);
        Select dayDropDown = new Select(wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(DAY_DOB_DROPDOWN))));
        dayDropDown.selectByVisibleText(day);

        // ----- MONTH -----
        logger.info("User chooses MONTH: {}", month);
        Select monthDropDown = new Select(wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(MONTH_DOB_DROPDOWN))));
        monthDropDown.selectByVisibleText(month);

        // ----- YEAR -----
        logger.info("User chooses YEAR: {}", year);
        Select yearDropDown = new Select(wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(YEAR_DOB_DROPDOWN))));
        yearDropDown.selectByVisibleText(year);
    }

    public void selectNewsletterCheckbox() {
        if (!isSelected(NEWSLETTER_CHECKBOX)) {
            logger.info(" =====> User Selects The Newsletter Checkbox <===== ");
            click(NEWSLETTER_CHECKBOX);
        }
    }

    public void selectSpecialOffersCheckbox() {
        if (!isSelected(SPECIAL_OFFERS_CHECKBOX)) {
            logger.info(" =====> User Selects The Special Offers Checkbox <===== ");

            click(SPECIAL_OFFERS_CHECKBOX);
        }
    }

    public void selectCommunicationCheckboxes() {
        selectNewsletterCheckbox();
        selectSpecialOffersCheckbox();
    }

    public void enterAddressInformation(String firstName, String lastName, String company, String addressOne, String addressTwo, String state, String city, String zipCode, String phoneNumber) {
        validateRequiredValue(firstName, "First name");
        validateRequiredValue(lastName, "Last name");
        validateRequiredValue(addressOne, "Address line one");
        validateRequiredValue(state, "State");
        validateRequiredValue(city, "City");
        validateRequiredValue(zipCode, "ZIP code");
        validateRequiredValue(phoneNumber, "Phone number");

        logger.info(" =====> User Enters Address Information <===== ");

        type(FIRST_NAME_INPUT, firstName);
        type(LAST_NAME_INPUT, lastName);

        enterOptionalValue(COMPANY_INPUT, company);

        type(ADDRESS_FIRST_LINE_INPUT, addressOne);

        enterOptionalValue(ADDRESS_SECOND_LINE_INPUT, addressTwo);

        selectByVisibleText(COUNTRY_DROPDOWN, "United States");

        type(STATE_INPUT, state);
        type(CITY_INPUT, city);
        type(ZIP_CODE_INPUT, zipCode);
        type(MOBILE_NUMBER_INPUT, phoneNumber);
    }

    public AccountCreatedPage clickCreateAccountButton() {
        logger.info(" =====> User Clicks The Create Account Button <===== ");
        click(CREATE_ACCOUNT_BUTTON);
        return new AccountCreatedPage(driver);
    }

    public AccountCreatedPage completeAccountRegistration(String password, String firstName, String lastName, String company, String addressOne, String addressTwo, String state, String city, String zipCode, String phoneNumber) {
        enterPassword(password);
        selectRandomDateOfBirth();
        selectCommunicationCheckboxes();

        enterAddressInformation(firstName, lastName, company, addressOne, addressTwo, state, city, zipCode, phoneNumber);

        return clickCreateAccountButton();
    }

    public boolean isCreateAccountButtonDisplayed() {
        return isDisplayed(CREATE_ACCOUNT_BUTTON);
    }

    public boolean isCreateAccountButtonEnabled() {
        return isEnabled(CREATE_ACCOUNT_BUTTON);
    }

    private void enterOptionalValue(By locator, String value) {
        if (value != null && !value.isBlank()) {
            type(locator, value);
        }
    }

    private void validateRequiredValue(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }
}