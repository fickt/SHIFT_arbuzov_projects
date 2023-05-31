package ru.cft.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.storage.Storage;

public class Consumer {
    private static final Logger logger = LogManager.getLogger();
    private final Storage storage;
    private final int consumerCount;
    private final int consumerTime;

    public Consumer(Storage storage, int consumerCount, int consumerTime) {
        this.storage = storage;
        this.consumerCount = consumerCount;
        this.consumerTime = consumerTime;
    }
    public void startConsume() {
        int count = 0;
        while (count < consumerCount) {
            var thread = new Thread(new ConsumerTask());
            thread.setName("Consumer №" + count);
            thread.start();
            count++;
        }
    }

    private class ConsumerTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                logger.info(Thread.currentThread().getName() + ", state: active");
                storage.consume();
                try {
                    logger.info(Thread.currentThread().getName() + ", state: sleeping");
                    Thread.sleep(consumerTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
