package org.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.constants.Constants;
import org.example.core.ConfigReader;
import org.example.core.DriverFactory;
import org.example.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp() {
        logger.info(" =====> Starting Scenario... <===== ");
        DriverFactory.setDriver(Constants.DEFAULT_BROWSER, Constants.DEFAULT_HEADLESS);
    }

    @After
    public void tearDown(Scenario scenario) {

        try {
            if (scenario.isFailed()) {

                logger.error(" =====> Scenario FAILED: {} <===== ", scenario.getName());
                byte[] screenshot = ScreenshotUtils.takeScreenshotAsBytes();
                scenario.attach(screenshot, "image/png", scenario.getName());
                ScreenshotUtils.takeScreenshotAsFile(scenario.getName());

            } else {
                logger.info(" =====> Scenario PASSED: {} <===== ", scenario.getName());
            }

        } catch (IllegalAccessException e) {
            logger.warn("Issue during teardown or screenshot capture: {}", e.getMessage());
        } finally {
            logger.info(" =====> Closing Browser... <===== ");
            DriverFactory.quitDriver();
        }
    }
}