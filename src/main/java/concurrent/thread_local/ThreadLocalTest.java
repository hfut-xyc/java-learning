package concurrent.thread_local;

import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadLocalTest {

    private static final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    static class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                threadLocal.set(threadLocal.get() + 1);
                log.info("{}", threadLocal.get());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    public void test1() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(new Task());
        }
        executor.shutdown();
    }

}
