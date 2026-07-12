package org.example.core;

import org.example.constants.Constants;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private static final List<String> BLOCKED_URL_PATTERNS = List.of("*://*.doubleclick.net/*", "*://*.googlesyndication.com/*", "*://*.googleadservices.com/*", "*://*.adservice.google.com/*", "*://*.adsystem.com/*", "*://*.facebook.net/*", "*://*analytics*/*");

    private DriverFactory() {
        throw new IllegalStateException("DriverFactory cannot be instantiated");
    }

    public static void setDriver(String browser, boolean headless) {
        if (DRIVER.get() != null) {
            throw new IllegalStateException("WebDriver is already initialized for thread: " + Thread.currentThread().threadId());
        }

        String selectedBrowser = browser == null || browser.isBlank() ? Constants.DEFAULT_BROWSER.toLowerCase(Locale.ROOT) : browser.trim().toLowerCase(Locale.ROOT);

        logger.info("Initializing browser: {}, headless: {}, thread: {}", selectedBrowser, headless, Thread.currentThread().threadId());

        WebDriver driver = switch (selectedBrowser) {
            case "chrome" -> createChromeDriver(headless);
            case "firefox" -> createFirefoxDriver(headless);
            case "edge" -> createEdgeDriver(headless);

            default ->
                    throw new IllegalArgumentException("Unsupported browser: " + selectedBrowser + ". Supported browsers: chrome, firefox, edge");
        };

        try {
            configureDriver(driver);
            configureNetworkBlocking(driver);

            DRIVER.set(driver);

            logger.info("WebDriver initialized successfully for thread: {}", Thread.currentThread().threadId());
        } catch (RuntimeException exception) {
            safelyQuitDriver(driver);

            throw new IllegalStateException("Failed to initialize WebDriver for browser: " + selectedBrowser, exception);
        }
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        options.addArguments("--disable-notifications", "--disable-popup-blocking", "--disable-dev-shm-usage", "--no-sandbox");

        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        if (headless) {
            options.addArguments("-headless");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        EdgeOptions options = new EdgeOptions();

        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        options.addArguments("--disable-notifications", "--disable-popup-blocking", "--disable-dev-shm-usage", "--no-sandbox");

        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        return new EdgeDriver(options);
    }

    private static void configureDriver(WebDriver driver) {
        /*
         * Use explicit waits through WebDriverWait and WaitUtils.
         * Do not combine a nonzero implicit wait with explicit waits.
         */
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT);

        driver.manage().timeouts().scriptTimeout(Constants.SCRIPT_TIMEOUT);

        driver.manage().deleteAllCookies();

        try {
            driver.manage().window().maximize();
        } catch (RuntimeException exception) {
            logger.warn("Browser window could not be maximized: {}", exception.getMessage());
        }
    }

    private static void configureNetworkBlocking(WebDriver driver) {
        if (!(driver instanceof ChromiumDriver chromiumDriver)) {
            logger.debug("CDP network blocking is not supported for browser: {}", driver.getClass().getSimpleName());

            return;
        }

        try {
            chromiumDriver.executeCdpCommand("Network.enable", Map.of());

            chromiumDriver.executeCdpCommand("Network.setBlockedURLs", Map.of("urls", BLOCKED_URL_PATTERNS));

            logger.info("CDP network blocking enabled. " + "Blocked URL patterns: {}", BLOCKED_URL_PATTERNS.size());
        } catch (RuntimeException exception) {
            logger.warn("Could not configure CDP network blocking: {}", exception.getMessage());
        }
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for thread: " + Thread.currentThread().threadId());
        }

        return driver;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();

        try {
            if (driver != null) {
                logger.info("Closing WebDriver for thread: {}", Thread.currentThread().threadId());

                driver.quit();
            }
        } catch (RuntimeException exception) {
            logger.warn("An issue occurred while closing WebDriver: {}", exception.getMessage());
        } finally {
            DRIVER.remove();
        }
    }

    private static void safelyQuitDriver(WebDriver driver) {
        if (driver == null) {
            return;
        }

        try {
            driver.quit();
        } catch (RuntimeException exception) {
            logger.warn("Could not close WebDriver after initialization failure: {}", exception.getMessage());
        }
    }
}