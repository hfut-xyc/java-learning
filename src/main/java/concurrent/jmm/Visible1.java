package concurrent.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * @date 2022-9-4
 **/
@Slf4j
public class Visible1 {
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            log.info("start");
            while (flag) {
                // nothing here
            }
            log.info("stop");
        }).start();

        Thread.sleep(1000);
        flag = false;
    }
}
