package concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicTest {

    private static int count1 = 0;
    private static final AtomicInteger count2 = new AtomicInteger(0);
    private static final AtomicIntegerArray array = new AtomicIntegerArray(100);

    public void test1() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    count1++;
                    count2.incrementAndGet();
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
        System.out.println(count1);
        System.out.println(count2.get());
    }

}
