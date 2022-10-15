package concurrent4.producer_consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @date 2022-10-14
 **/
public class ProducerConsumerTest3 {

    static class MessageQueue {
        private int capacity;
        private BlockingQueue<String> queue;

        public MessageQueue(int capacity) {
            this.capacity = capacity;
            this.queue = new ArrayBlockingQueue<>(capacity);
        }
    }
}
