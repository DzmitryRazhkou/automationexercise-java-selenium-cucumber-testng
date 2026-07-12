package org.example.utils;

import org.example.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class WaitUtils {

    private WaitUtils() {
        throw new IllegalStateException("WaitUtils cannot be instantiated");
    }

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return waitForVisible(driver, locator, Constants.EXPLICIT_WAIT);
    }

    public static WebElement waitForVisible(WebDriver driver, By locator, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator) {
        return waitForAllVisible(driver, locator, Constants.EXPLICIT_WAIT);
    }

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return waitForClickable(driver, locator, Constants.EXPLICIT_WAIT);
    }

    public static WebElement waitForClickable(WebDriver driver, By locator, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForInvisible(WebDriver driver, By locator) {
        return waitForInvisible(driver, locator, Constants.EXPLICIT_WAIT);
    }

    public static boolean waitForInvisible(WebDriver driver, By locator, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForUrlContains(WebDriver driver, String expectedUrlPart) {
        return createWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.urlContains(expectedUrlPart));
    }

    public static boolean waitForTitleContains(WebDriver driver, String expectedTitlePart) {
        return createWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.titleContains(expectedTitlePart));
    }

    public static boolean waitForText(WebDriver driver, By locator, String expectedText) {
        return createWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public static <T> T waitForCondition(WebDriver driver, ExpectedCondition<T> condition, Duration timeout) {
        return createWait(driver, timeout).until(condition);
    }

    private static WebDriverWait createWait(WebDriver driver, Duration timeout) {
        return (WebDriverWait) new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class);
    }
}