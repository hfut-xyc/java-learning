package concurrent.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class WaitNotifyTest2 {

    static class MessageQueue {

        private int capacity;
        private LinkedList<String> list = new LinkedList<>();

        public MessageQueue(int capacity) {
            this.capacity = capacity;
        }

        public void put(String msg) throws InterruptedException {
            synchronized (this) {
                while (list.size() == capacity) {
                    log.debug("Wait, queue is full");
                    this.wait();
                }
                log.debug("put");
                list.addLast(msg);
                this.notifyAll();
            }
        }

        public String take() throws InterruptedException {
            synchronized (this) {
                while (list.isEmpty()) {
                    log.debug("Wait, queue is empty");
                    this.wait();
                }
                log.debug("take");
                String msg = list.removeFirst();
                this.notifyAll();
                return msg;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue(2);

        for (int i = 1; i <= 4; i++) {
            new Thread(() -> {
                try {
                    messageQueue.put("message");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Producer-" + i).start();
        }

        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                try {
                    messageQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Consumer-" + i).start();
        }
    }
}
