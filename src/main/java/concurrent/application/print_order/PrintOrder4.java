package concurrent.application.print_order;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class PrintOrder4 {
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
        new Thread(PrintOrder4::first).start();
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
