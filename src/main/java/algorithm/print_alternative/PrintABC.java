package algorithm.print_alternative;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class PrintABC {
    private static final int COUNT = 5;
    private static final Semaphore first = new Semaphore(1);
    private static final Semaphore second = new Semaphore(0);
    private static final Semaphore third = new Semaphore(0);


    public static void first() throws InterruptedException {
        for (int i = 0; i < COUNT; i++) {
            first.acquire();
            log.info("A");
            second.release();
        }
    }

    public static void second() throws InterruptedException {
        for (int i = 0; i < COUNT; i++) {
            second.acquire();
            log.info("B");
            third.release();
        }
    }

    public static void third() throws InterruptedException {
        for (int i = 0; i < COUNT; i++) {
            third.acquire();
            log.info("C");
            first.release();
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
