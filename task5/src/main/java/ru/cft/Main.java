package ru.cft;

import ru.cft.consumer.Consumer;
import ru.cft.producer.Producer;
import ru.cft.propertiesloader.PropertiesLoader;
import ru.cft.storage.Storage;

public class Main {

    public static void main(String[] args) {
        var properties = new PropertiesLoader();

        var storageSize = properties.getStorageSize();
        var producerTime = properties.getProducerTime();
        var producerCount = properties.getProducerCount();
        var consumerTime = properties.getConsumerTime();
        var consumerCount = properties.getConsumerCount();

        var storage = new Storage(storageSize);
        var consumption = new Consumer(storage, consumerCount, consumerTime);
        var production = new Producer(storage, producerCount, producerTime);

        consumption.startConsume();
        production.startProduce();
    }

}