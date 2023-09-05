package concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class CyclicBarrierTest {

    private static final Random random = new Random();

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            log.info("已凑齐一个4人小队，准备出发");
        });

        for (int i = 0; i < 8; i++) {
            int _i = i;
            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(1000));
                    log.info(_i + "号玩家已加入匹配");
                    barrier.await();
                    log.info(_i + "号玩家已出发");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
