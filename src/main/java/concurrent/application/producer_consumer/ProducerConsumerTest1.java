package concurrent.application.producer_consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.stream.IntStream;

@Slf4j
public class ProducerConsumerTest1 {

    static class MessageQueue {

        private int capacity;
        private final LinkedList<String> list = new LinkedList<>();

        public MessageQueue(int capacity) {
            this.capacity = capacity;
        }

        public void put(String msg) throws InterruptedException {
            synchronized (this) {
                while (list.size() == capacity) {
                    this.wait();
                }
                list.addFirst(msg);
                log.info("put, size = {}", list.size());
                this.notifyAll();
            }
        }

        public void take() throws InterruptedException {
            synchronized (this) {
                while (list.isEmpty()) {
                    this.wait();
                }
                list.removeLast();
                log.info("take, size = {}", list.size());
                this.notifyAll();
            }
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
