package org.example.core;

import org.example.constants.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private ConfigReader() {
    }

    private static void loadProperties() {
        String env = System.getProperty("env", "stag");
        String configFile = String.format(Constants.CONFIG_PATH, env);

        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(configFile)) {

            if (inputStream == null) {
                throw new RuntimeException("Config file not found: " + configFile);
            }

            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + configFile, e);
        }
    }

    public static String get(String key) {
        String value = System.getProperty(key);

        if (value != null && !value.isBlank()) {
            return value;
        }

        value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException("Property not found: " + key);
        }

        return value;
    }

    public static String getBaseUrl() {
        return get("base.url");
    }
}