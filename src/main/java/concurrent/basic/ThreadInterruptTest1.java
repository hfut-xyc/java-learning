package concurrent.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @date 2022-9-25
 **/
@Slf4j
public class ThreadInterruptTest1 {

    public static void test1() throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                log.info("test");
                boolean flag = Thread.currentThread().isInterrupted();
                if (flag) {
                    log.info("break");
                    break;
                }
            }
        });
        t.start();
        t.interrupt();
    }

    public static void test2() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.info("sleep...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.info("{}", Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
        });
        t.start();

        Thread.sleep(1000);
        t.interrupt();
    }


    public static void test3() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.info("park1");
            LockSupport.park();
            log.info("unpark");
            log.info("{}", Thread.currentThread().isInterrupted());

            LockSupport.park(); // 此处的park会失效
            log.info("park2");
        });
        t.start();

        Thread.sleep(1000);
        t.interrupt();
    }

    public static void test4() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.info("park1");
            LockSupport.park();
            log.info("unpark");
            log.info("{}", Thread.interrupted());

            LockSupport.park(); // 此处的park并不会失效
            log.info("park2");
        });
        t.start();

        Thread.sleep(1000);
        t.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        test1();
    }
}
