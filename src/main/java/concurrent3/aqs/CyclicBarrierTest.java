package concurrent3.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            log.info("4 people have been ready");
        });

        for (int i = 0; i < 12; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    log.info("ready");
                    barrier.await();
                    log.info("set out");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
