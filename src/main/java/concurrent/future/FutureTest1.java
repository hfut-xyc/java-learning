package concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class FutureTest1 {

    public static void main(String[] args) throws Exception {
        test3();
    }

    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "hello";
        });
        System.out.println(future.isDone());
        String result = future.get();
        System.out.println(future.isDone());
        System.out.println(result);

        executor.shutdown();
    }

    public static void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            int i = 1 / 0;
            return "hello";
        });
        try {
            future.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        executor.shutdown();
    }

    public static void test3() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            try {
                System.out.println("waiting...");
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return Thread.currentThread().getName();
        });

        //future.get(1000, TimeUnit.MILLISECONDS);
        boolean cancel = future.cancel(true);
        System.out.println(cancel);
        executor.shutdown();
    }

    public static void test4() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<String> future = executor.submit(() -> {
                Thread.sleep(1000);
                return Thread.currentThread().getName();
            });
            futures.add(future);
        }

        for (Future<String> future : futures) {
            System.out.println(future.get());
        }
        executor.shutdown();
    }

    public void test5() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(500);
            return "hello";
        });
        executor.submit(futureTask);
        System.out.println(futureTask.get());
        executor.shutdown();
    }

}
