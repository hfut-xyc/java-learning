package concurrent.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @date 2022-9-23
 **/
@Slf4j
public class ThreadDaemonTest {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                log.debug("test");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
        log.debug("主线程退出");
    }
}
