package ru.cft.propertiesloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final String PROPERTY_STORAGE_SIZE = "storage.size";
    private static final String PROPERTY_PRODUCER_TIME = "producer.time";
    private static final String PROPERTY_PRODUCER_COUNT = "producer.count";
    private static final String PROPERTY_CONSUMER_TIME = "consumer.time";
    private static final String PROPERTY_CONSUMER_COUNT = "consumer.count";
    private static final String APPLICATION_PROPERTIES = "application.properties";

    private final int storageSize;
    private final int producerTime;
    private final int producerCount;
    private final int consumerTime;
    private final int consumerCount;

    public PropertiesLoader() {
        try {
            Properties properties = loadProperties();
            storageSize = Integer.parseInt(properties.getProperty(PROPERTY_STORAGE_SIZE));
            producerTime = Integer.parseInt(properties.getProperty(PROPERTY_PRODUCER_TIME));
            producerCount = Integer.parseInt(properties.getProperty(PROPERTY_PRODUCER_COUNT));
            consumerTime = Integer.parseInt(properties.getProperty(PROPERTY_CONSUMER_TIME));
            consumerCount = Integer.parseInt(properties.getProperty(PROPERTY_CONSUMER_COUNT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getStorageSize() {
        return this.storageSize;
    }

    public int getProducerCount() {
        return this.producerCount;
    }

    public int getProducerTime() {
        return this.producerTime;
    }

    public int getConsumerCount() {
        return this.consumerCount;
    }

    public int getConsumerTime() {
        return this.consumerTime;
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
