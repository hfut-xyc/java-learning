package atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {

    private static int count1 = 0;
    private static final AtomicInteger count2 = new AtomicInteger(0);
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void test1() {
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    count1++;
                }
            });
        }
        executor.shutdown();
    }

    public static void test2() {
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    count2.incrementAndGet();
                }
            });
        }
        executor.shutdown();
    }

    public static void main(String[] args) {

    }
}
