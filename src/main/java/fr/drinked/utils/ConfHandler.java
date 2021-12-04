package fr.drinked.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfHandler {

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream input = new FileInputStream("src/main/resources/fr/drinked/config/config.properties");
            properties.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getSetting(String key) {
        return properties.getProperty(key);
    }

}
