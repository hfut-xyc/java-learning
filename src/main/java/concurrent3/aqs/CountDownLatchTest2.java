package concurrent3.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class CountDownLatchTest2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                try {
                    log.info("ready to run");
                    latch.await();
                    log.info("start to run");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(3000);
        log.info("Signal gun fired, match start");
        latch.countDown();
    }
}
