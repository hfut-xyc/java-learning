package concurrent.application.print_alternative;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class PrintFooBar2 {
    private static final int loopCount = 3;
    private static volatile boolean flag = true;
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void foo() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            lock.lock();
            try {
                while (!flag) {
                    condition.await();
                }
                log.info("foo");
                flag = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void bar() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            lock.lock();
            try {
                while (flag) {
                    condition.await();
                }
                log.info("bar");
                flag = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                foo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                bar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}