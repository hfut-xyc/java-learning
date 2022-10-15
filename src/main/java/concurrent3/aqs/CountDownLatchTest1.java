package concurrent3.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class CountDownLatchTest1 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                    log.info("finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        log.info("Waiting for all tasks to finish");
        latch.await();
        log.info("All tasks finished");
    }
}
