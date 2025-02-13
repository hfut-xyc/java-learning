package concurrent.application.producer_consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @date 2022-10-14
 **/
@Slf4j
public class ProducerConsumerTest2 {

    static class MessageQueue {
        private int capacity;
        private final LinkedList<String> list = new LinkedList<>();
        private final Lock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public MessageQueue(int capacity) {
            this.capacity = capacity;
        }

        public void put(String msg) throws InterruptedException {
            lock.lock();
            while (list.size() == capacity) {
                notFull.await();
            }
            list.addFirst(msg);
            log.info("put, size = {}", list.size());
            notEmpty.signalAll();
            lock.unlock();
        }

        public void take() throws InterruptedException {
            lock.lock();
            while (list.size() == 0) {
                notEmpty.await();
            }
            list.removeLast();
            log.info("take, size = {}", list.size());
            notFull.signalAll();
            lock.unlock();
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
