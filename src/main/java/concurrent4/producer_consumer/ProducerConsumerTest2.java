package concurrent4.producer_consumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @date 2022-10-14
 **/
public class ProducerConsumerTest2 {

    static class MessageQueue {
        private int capacity;
        private LinkedList<String> list = new LinkedList<>();
        private Lock lock = new ReentrantLock();
        private Condition notFull = lock.newCondition();
        private Condition notEmpty = lock.newCondition();

        public MessageQueue(int capacity) {
            this.capacity = capacity;
        }


    }
}
