package concurrent3.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledThreadPoolTest {

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
        test1();
    }


    public static void test1() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        executorService.scheduleAtFixedRate(new Task(), 1, 1, TimeUnit.SECONDS);
    }
}
