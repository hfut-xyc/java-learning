package concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class PrintInOrder1 {

    private static Semaphore second = new Semaphore(0);
    private static Semaphore third = new Semaphore(0);

    public static void first() {
        log.info("first");
        second.release();
    }

    public static void second() throws InterruptedException {
        second.acquire();
        log.info("second");
        third.release();
    }

    public static void third() throws InterruptedException {
        third.acquire();
        log.info("third");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> first());
        Thread t2 = new Thread(() -> {
            try {
                second();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                third();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
