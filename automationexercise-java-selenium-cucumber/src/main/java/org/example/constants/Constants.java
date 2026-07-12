package org.example.constants;

import java.time.Duration;

public final class Constants {

    public static final String DEFAULT_BROWSER = "chrome";
    public static final boolean DEFAULT_HEADLESS = false;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
    public static final Duration SHORT_TIMEOUT = Duration.ofSeconds(5);
    public static final Duration LONG_TIMEOUT = Duration.ofSeconds(30);
    public static final Duration IMPLICIT_WAIT = Duration.ZERO;
    public static final Duration EXPLICIT_WAIT = Duration.ofSeconds(15);
    public static final String CONFIG_PATH = "config/%s.properties";
    public static final String SCREENSHOTS_DIR = "target/screenshots/";


    public static final Duration SHORT_WAIT = Duration.ofSeconds(5);
    public static final Duration LONG_WAIT = Duration.ofSeconds(30);
    public static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(60);
    public static final Duration SCRIPT_TIMEOUT = Duration.ofSeconds(30);

    private Constants() {
        throw new IllegalStateException("Constants class cannot be instantiated");
    }
}