package ru.cft.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.resource.Resource;

import java.util.LinkedList;
import java.util.Queue;

/*
1.Писать в лог сообщение: время,номер и тип потока,id-ресурса,событие(произведен или потреблен)
2.Писать в лог сообщение: количество ресурсов на складе при доставке/потреблении на склад
3.Писать в лог сообщение: время,номер и тип потока, событие(когда потоки переходят в режим ожидания или возобновляют работу)
*/
public class Storage {
    private static final Logger logger = LogManager.getLogger();
    private final int storageSize;
    private final Queue<Resource> storage = new LinkedList<>();

    public Storage(int storageSize) {
        this.storageSize = storageSize;
    }

    public synchronized void produce(Resource resource) {
        while (storage.size() >= storageSize) {
            logger.info(Thread.currentThread().getName() + ", state: waiting...");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        storage.offer(resource);
        logger.info(Thread.currentThread().getName() + ", resource ID: " + resource.getId() + " has been produced!");
        logger.info("Resources total: " +storage.size());
        notify();
    }

    public synchronized Resource consume() {
        while (storage.size() == 0) {
            logger.info(Thread.currentThread().getName() + ", state: waiting...");
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        logger.info(Thread.currentThread().getName() + ", resource ID: " + storage.peek().getId() + " has been consumed!");
        Resource resource = storage.poll();
        logger.info("Resources total: " + storage.size());
        notify();
        return resource;
    }
}

