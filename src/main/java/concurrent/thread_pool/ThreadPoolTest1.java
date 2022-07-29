package concurrent.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolTest1 {

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                log.info("hello");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        test4();
    }

    public static void test1() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Task());
        }
    }

    
    public static void test2() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
    }

    
    public static void test3() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
    }

    
    public static void test4() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        executorService.scheduleAtFixedRate(new Task(), 1, 1, TimeUnit.SECONDS);
    }
}
