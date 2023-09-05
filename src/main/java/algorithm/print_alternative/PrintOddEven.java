package algorithm.print_alternative;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintOddEven {

    private static final int loopCount = 5;
    private static boolean flag = true;
    private static final Object lock = new Object();

    public static void odd() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            synchronized (lock) {
                while (!flag) {
                    lock.wait();
                }
                if (i % 2 == 1) {
                    log.info("{}", i);
                }
                flag = false;
                lock.notify();
            }
        }
    }

    public static void even() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            synchronized (lock) {
                while (flag) {
                    lock.wait();
                }
                if (i % 2 == 0) {
                    log.info("{}", i);
                }
                flag = true;
                lock.notify();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                odd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "odd");

        Thread t2 = new Thread(() -> {
            try {
                even();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "even");
        t1.start();
        t2.start();
    }
}
