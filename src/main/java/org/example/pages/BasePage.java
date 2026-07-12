package org.example.pages;

import org.example.constants.Constants;
import org.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class BasePage {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    private static final String NUMBER_CHARACTERS = "0123456789";

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*";

    private static final String ALL_PASSWORD_CHARACTERS = UPPERCASE_CHARACTERS + LOWERCASE_CHARACTERS + NUMBER_CHARACTERS + SPECIAL_CHARACTERS;

    protected final Logger logger;
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver must not be null.");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        this.logger = LoggerFactory.getLogger(getClass());
    }

    protected void open(String url) {
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }

    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected WebElement getVisibleElement(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }

    protected List<WebElement> getVisibleElements(By locator) {
        return WaitUtils.waitForAllVisible(driver, locator);
    }

    protected void click(By locator) {
        logger.debug("Clicking element: {}", locator);

        try {
            WaitUtils.waitForClickable(driver, locator).click();
        } catch (ElementClickInterceptedException exception) {
            logger.warn("Normal click was intercepted. Using JavaScript click: {}", locator);

            WebElement element = WaitUtils.waitForVisible(driver, locator);

            executeJavaScript("arguments[0].click();", element);
        }
    }

    protected void clickElementFromList(By locator, int index) {
        List<WebElement> elements = WaitUtils.waitForAllVisible(driver, locator);

        validateIndex(elements, index, locator);

        elements.get(index).click();
    }

    protected void clickElementByText(By locator, String expectedText) {
        WebElement matchingElement = findElements(locator).stream().filter(WebElement::isDisplayed).filter(element -> element.getText().trim().equalsIgnoreCase(expectedText.trim())).findFirst().orElseThrow(() -> new NoSuchElementException("No visible element matching text '" + expectedText + "' was found using locator: " + locator));

        matchingElement.click();
    }

    protected void type(By locator, String text) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);

        element.clear();
        element.sendKeys(text);
    }

    protected void clearUsingKeyboard(By locator) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);

        element.click();

        String operatingSystem = System.getProperty("os.name", "").toLowerCase();

        Keys controlKey = operatingSystem.contains("mac") ? Keys.COMMAND : Keys.CONTROL;

        element.sendKeys(Keys.chord(controlKey, "a"), Keys.DELETE);
    }

    protected void replaceText(By locator, String text) {
        clearUsingKeyboard(locator);
        findElement(locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtils.waitForVisible(driver, locator).getText().trim();
    }

    protected List<String> getTexts(By locator) {
        return findElements(locator).stream().filter(WebElement::isDisplayed).map(WebElement::getText).map(String::trim).toList();
    }

    protected String getAttribute(By locator, String attribute) {
        return WaitUtils.waitForVisible(driver, locator).getAttribute(attribute);
    }

    protected String getDomAttribute(By locator, String attribute) {
        return WaitUtils.waitForVisible(driver, locator).getDomAttribute(attribute);
    }

    protected boolean isDisplayed(By locator) {
        return findElements(locator).stream().anyMatch(element -> {
            try {
                return element.isDisplayed();
            } catch (StaleElementReferenceException exception) {
                return false;
            }
        });
    }

    protected boolean isDisplayed(By locator, Duration timeout) {
        try {
            return WaitUtils.waitForVisible(driver, locator, timeout).isDisplayed();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    protected boolean isNotDisplayed(By locator) {
        return !isDisplayed(locator);
    }

    protected boolean isPresent(By locator) {
        return !findElements(locator).isEmpty();
    }

    protected boolean isNotPresent(By locator) {
        return findElements(locator).isEmpty();
    }

    protected int getElementCount(By locator) {
        return findElements(locator).size();
    }

    protected boolean isEnabled(By locator) {
        return findElements(locator).stream().filter(WebElement::isDisplayed).findFirst().map(WebElement::isEnabled).orElse(false);
    }

    protected boolean isSelected(By locator) {
        return findElements(locator).stream().filter(WebElement::isDisplayed).findFirst().map(WebElement::isSelected).orElse(false);
    }

    protected Optional<WebElement> findOptionalElement(By locator) {
        return findElements(locator).stream().filter(WebElement::isDisplayed).findFirst();
    }

    protected void selectByVisibleText(By locator, String text) {
        WebElement dropdown = WaitUtils.waitForVisible(driver, locator);

        new Select(dropdown).selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        WebElement dropdown = WaitUtils.waitForVisible(driver, locator);

        new Select(dropdown).selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        WebElement dropdown = WaitUtils.waitForVisible(driver, locator);

        new Select(dropdown).selectByIndex(index);
    }

    protected String getSelectedOption(By locator) {
        WebElement dropdown = WaitUtils.waitForVisible(driver, locator);

        return new Select(dropdown).getFirstSelectedOption().getText().trim();
    }

    protected void waitForElementToDisappear(By locator) {
        WaitUtils.waitForInvisible(driver, locator);
    }

    protected void waitForText(By locator, String expectedText) {
        WaitUtils.waitForText(driver, locator, expectedText);
    }

    protected void scrollIntoView(By locator) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);

        executeJavaScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected void scrollToTop() {
        executeJavaScript("window.scrollTo({top: 0, behavior: 'instant'});");
    }

    protected void scrollToBottom() {
        executeJavaScript("window.scrollTo({top: document.body.scrollHeight, " + "behavior: 'instant'});");
    }

    protected void executeJavaScript(String script, Object... arguments) {
        ((JavascriptExecutor) driver).executeScript(script, arguments);
    }

    protected void jsClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        executeJavaScript("arguments[0].click();", element);

        logger.debug("JS click performed: {}", locator);
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void navigateBack() {
        driver.navigate().back();
    }

    protected void navigateForward() {
        driver.navigate().forward();
    }

    protected void switchToNewWindow(String originalWindowHandle) {
        Set<String> windowHandles = driver.getWindowHandles();

        String newWindowHandle = windowHandles.stream().filter(handle -> !handle.equals(originalWindowHandle)).findFirst().orElseThrow(() -> new IllegalStateException("A new browser window was not found."));

        driver.switchTo().window(newWindowHandle);
    }

    protected void switchToFrame(By locator) {
        WebElement frame = WaitUtils.waitForVisible(driver, locator);

        driver.switchTo().frame(frame);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public String generatePassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 4 characters.");
        }

        StringBuilder password = new StringBuilder(length);

        password.append(getRandomCharacter(UPPERCASE_CHARACTERS));

        password.append(getRandomCharacter(LOWERCASE_CHARACTERS));

        password.append(getRandomCharacter(NUMBER_CHARACTERS));

        password.append(getRandomCharacter(SPECIAL_CHARACTERS));

        while (password.length() < length) {
            password.append(getRandomCharacter(ALL_PASSWORD_CHARACTERS));
        }

        String generatedPassword = shuffleCharacters(password.toString());

        logger.debug("Generated a password with length: {}", generatedPassword.length());

        return generatedPassword;
    }

    private char getRandomCharacter(String characters) {
        int randomIndex = RANDOM.nextInt(characters.length());

        return characters.charAt(randomIndex);
    }

    private String shuffleCharacters(String value) {
        char[] characters = value.toCharArray();

        for (int currentIndex = characters.length - 1; currentIndex > 0; currentIndex--) {
            int randomIndex = RANDOM.nextInt(currentIndex + 1);

            char temporaryCharacter = characters[currentIndex];

            characters[currentIndex] = characters[randomIndex];

            characters[randomIndex] = temporaryCharacter;
        }

        return new String(characters);
    }

    private void validateIndex(List<WebElement> elements, int index, By locator) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is invalid for locator " + locator + ". Number of elements: " + elements.size());
        }
    }

    protected String[] generateYear() {

        List<String> years = new ArrayList<>();
        int currentYear = Year.now().getValue();
        int minYear = currentYear - 100;

        for (int i = currentYear; i >= minYear; i--) {
            years.add(String.valueOf(i));
        }
        return years.toArray(new String[0]);
    }

    protected boolean verifyTextContent(By locator, String expectedText) {
        logger.debug("Waiting For Visibility Of Web Element: {}", locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        String actualText = element.getText().trim();
        expectedText = expectedText.trim();

        logger.debug("Expected: [{}] | Actual: [{}]", expectedText, actualText);
        return actualText.equals(expectedText);
    }
}