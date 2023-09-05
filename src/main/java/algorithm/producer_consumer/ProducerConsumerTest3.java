package algorithm.producer_consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * @date 2022-10-14
 **/
@Slf4j
public class ProducerConsumerTest3 {

    static class MessageQueue {
        private final LinkedList<String> list = new LinkedList<>();
        private final Semaphore mutex;
        private final Semaphore produceSemaphore;
        private final Semaphore consumeSemaphore;

        public MessageQueue(int capacity) {
            mutex = new Semaphore(1);
            produceSemaphore = new Semaphore(capacity);
            consumeSemaphore = new Semaphore(0);
        }

        public void put(String msg) throws InterruptedException {
            produceSemaphore.acquire();
            mutex.acquire();
            list.addFirst(msg);
            log.info("put, size = {}", list.size());
            mutex.release();
            consumeSemaphore.release();
        }

        public void take() throws InterruptedException {
            consumeSemaphore.acquire();
            mutex.acquire();
            list.removeLast();
            log.info("take, size = {}", list.size());
            mutex.release();
            produceSemaphore.release();
        }
    }

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);

        IntStream.rangeClosed(1, 4).forEach(i -> {
            new Thread(() -> {
                try {
                    messageQueue.put("message");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Producer-" + i).start();
        });

        IntStream.rangeClosed(1, 3).forEach(i -> {
            new Thread(() -> {
                try {
                    messageQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Consumer-" + i).start();
        });
    }
}
