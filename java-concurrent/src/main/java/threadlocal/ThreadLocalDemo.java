package threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {

    private static final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void test() throws InterruptedException{
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    threadLocal.set(threadLocal.get() + 1);
                    System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
                }
            });
        }
        executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }
}
