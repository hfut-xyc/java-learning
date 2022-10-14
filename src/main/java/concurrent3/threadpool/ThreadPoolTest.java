package concurrent3.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolTest {

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                log.info("finish");
            } catch (InterruptedException e) {
                log.info("interrupt");
            }
        }
    }

    /**
     * shutdown
     */
    public static void test1() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            executor.execute(new Task());
        }
        executor.shutdown();
        log.info("{}", executor.isShutdown());
        executor.execute(new Task());
    }

    /**
     * shutdownNow
     */
    public static void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            executor.execute(new Task());
        }
        List<Runnable> list = executor.shutdownNow();
        log.info("{}", executor.isShutdown());
        log.info("{}", list);
    }

    /**
     * isTerminated
     */
    public static void test3() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            executor.execute(new Task());
        }
        executor.shutdown();
        log.info("{}", executor.isTerminated());
        Thread.sleep(3000);
        log.info("{}", executor.isTerminated());
    }

    /**
     * awaitTermination
     */
    public static void test4() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            executor.execute(new Task());
        }
        executor.shutdown();
        boolean flag = executor.awaitTermination(3, TimeUnit.SECONDS);
        log.info("{}", flag);
    }


    public static void main(String[] args) throws InterruptedException {
        test4();
    }

}
