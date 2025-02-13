package concurrent.application.print_alternative;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintFooBar1 {
    private static final int loopCount = 3;
    private static volatile boolean flag = true;
    private static final Object lock = new Object();

    public static void foo() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            synchronized (lock) {
                while (!flag) {
                    lock.wait();
                }
                log.info("foo");
                flag = false;
                lock.notify();
            }
        }
    }

    public static void bar() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            synchronized (lock) {
                while (flag) {
                    lock.wait();
                }
                log.info("bar");
                flag = true;
                lock.notify();
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