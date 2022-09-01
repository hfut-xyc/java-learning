package concurrent.cases;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintOrder1 {
    private static final int loopCount = 3;
    private static volatile int flag = 1;
    private static final Object lock = new Object();

    public static void first() throws InterruptedException {
        synchronized (lock) {
            while (flag != 1) {
                lock.wait();
            }
            log.info("first");
            flag = 2;
            lock.notifyAll();
        }
    }

    public static void second() throws InterruptedException {
        synchronized (lock) {
            while (flag != 2) {
                lock.wait();
            }
            log.info("second");
            flag = 3;
            lock.notifyAll();
        }
    }

    public static void third() throws InterruptedException {
        synchronized (lock) {
            while (flag != 3) {
                lock.wait();
            }
            log.info("third");
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