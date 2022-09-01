package concurrent.cases;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class PrintOrder4 {

    private static CountDownLatch second = new CountDownLatch(1);
    private static CountDownLatch third = new CountDownLatch(1);

    public static void first() {
        log.info("first");
        second.countDown();
    }

    public static void second() throws InterruptedException {
        second.await();
        log.info("second");
        third.countDown();
    }

    public static void third() throws InterruptedException {
        third.await();
        log.info("third");
    }

    public static void main(String[] args) {
        new Thread(PrintOrder3::first).start();
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
