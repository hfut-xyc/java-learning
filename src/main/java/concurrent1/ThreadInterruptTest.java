package concurrent1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @date 2022-9-25
 **/
@Slf4j
public class ThreadInterruptTest {

    public static void test1() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000); // wait, join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        Thread.sleep(1000);
        log.debug("interrupt");
        t.interrupt();
        log.debug("{}", t.isInterrupted());
    }

    public static void test2() throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                boolean flag = Thread.currentThread().isInterrupted();
                if (flag) {
                    log.debug("exit");
                    break;
                }
            }
        });
        t.start();

        Thread.sleep(1000);
        log.debug("interrupt");
        t.interrupt();
        log.debug("{}", t.isInterrupted());
    }

    public static void test3() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.info("park...");
            LockSupport.park();
            log.info("unpark");

        });
        t.start();

        Thread.sleep(2000);
        log.debug("interrupt");
        t.interrupt();
        log.debug("{}", t.isInterrupted());
    }

    public static void main(String[] args) throws InterruptedException {
        test3();
    }
}
