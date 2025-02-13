package concurrent.application.print_order;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class PrintOrder2 {
    private static volatile int flag = 1;
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void first() throws InterruptedException {
        lock.lock();
        try {
            log.info("first");
            flag = 2;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void second() throws InterruptedException {
        lock.lock();
        try {
            while (flag != 2) {
                condition.await();
            }
            log.info("second");
            flag = 3;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void third() throws InterruptedException {
        lock.lock();
        try {
            while (flag != 3) {
                condition.await();
            }
            log.info("third");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                first();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                second();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                third();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}