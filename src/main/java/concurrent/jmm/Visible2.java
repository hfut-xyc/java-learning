package concurrent.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * @date 2022-9-4
 **/
@Slf4j
public class Visible2 {
    private static boolean flag = true;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            log.info("start");
            while (true) {
                synchronized (lock) {
                    if (!flag) {
                        break;
                    }
                }
            }
            log.info("stop");
        }).start();

        Thread.sleep(1000);
        synchronized (lock) {
            flag = false;
        }
    }
}
