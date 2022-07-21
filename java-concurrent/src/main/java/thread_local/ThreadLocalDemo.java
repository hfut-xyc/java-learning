package thread_local;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {

    private static final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    static class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                threadLocal.set(threadLocal.get() + 1);
                System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void test1() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(new Task());
        }
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        test1();
    }
}
