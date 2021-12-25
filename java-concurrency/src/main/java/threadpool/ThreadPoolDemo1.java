package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolDemo1 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

    }

}
