package org.example.utils;

import org.example.constants.Constants;
import org.example.core.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static byte[] takeScreenshotAsBytes() throws IllegalAccessException {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static void takeScreenshotAsFile(String scenarioName) throws IllegalAccessException {
        File screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);

        String fileName = buildFileName(scenarioName);
        File destination = new File(Constants.SCREENSHOTS_DIR + fileName);

        try {
            Files.createDirectories(destination.getParentFile().toPath());
            Files.copy(screenshot.toPath(), destination.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }

    private static String buildFileName(String scenarioName) {
        return scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + "_thread_" + Thread.currentThread().threadId() + ".png";
    }
}