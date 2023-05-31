package ru.cft.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.resource.Resource;
import ru.cft.storage.Storage;

public class Producer {
    private static final Logger logger = LogManager.getLogger();
    private final Storage storage;
    private final int producerCount;
    private final int producerTime;

    public Producer(Storage storage, int producerCount, int producerTime) {
        this.storage = storage;
        this.producerCount = producerCount;
        this.producerTime = producerTime;
    }
    public void startProduce() {
        int count = 0;
        while (count < producerCount) {
            var thread = new Thread(new ProducerTask());
            thread.setName("Producer №" + count);
            thread.start();
            count++;
        }
    }

    private class ProducerTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                logger.info(Thread.currentThread().getName() + ", state: active");

                storage.produce(new Resource());
                try {
                    logger.info(Thread.currentThread().getName() + ", state: sleeping");
                    Thread.sleep(producerTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
