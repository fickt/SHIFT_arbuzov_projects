package ru.cft;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final String APPLICATION_PROPERTIES = "application.properties";
    private static final String PROPERTY_PORT = "port";
    private final int port;

    public PropertiesLoader() {
        try {
            Properties properties = loadProperties();
            port = Integer.parseInt(properties.getProperty(PROPERTY_PORT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return port;
    }

    private Properties loadProperties() throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(APPLICATION_PROPERTIES);
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }
}
