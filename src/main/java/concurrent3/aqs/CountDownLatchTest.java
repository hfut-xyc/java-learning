package concurrent3.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class CountDownLatchTest {

    private static final Random random = new Random();

    /**
     * 一个线程等待多个线程
     *
     * @throws InterruptedException
     */
    public static void test1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        String[] process = new String[5];

        for (int i = 0; i < 5; i++) {
            int _i = i;
            new Thread(() -> {
                for (int j = 1; j <= 100; j++) {
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    process[_i] = j + "%";
                    System.out.print("\r" + Arrays.toString(process));
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.print("\n所有任务加载完毕");
    }

    /**
     * 多个线程等待一个线程
     *
     * @throws InterruptedException
     */
    public static void test2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 1; i <= 5; i++) {
            int _i = i;
            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(1000));
                    log.info(_i + "号选手准备就绪");
                    latch.await();
                    log.info(_i + "号选手开跑");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(2000);
        log.info("发令枪响，比赛开始");
        latch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        test2();
    }
}
