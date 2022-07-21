package future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureDemo1 {

    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2500);
            return Thread.currentThread().getName();
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
            throw new ArithmeticException("/ by zero");
        });
        try {
            Thread.sleep(1500);
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    public static void test3() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return Thread.currentThread().getName();
        });

        try {
            String result = future.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            boolean cancel = future.cancel(true);
            System.out.println(cancel);
        }
        executor.shutdown();
    }

    public static void test4() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<String> future = executor.submit(() -> {
                Thread.sleep(500);
                return Thread.currentThread().getName();
            });
            futures.add(future);
        }

        for (Future future : futures) {
            System.out.println(future.get());
        }
        executor.shutdown();
    }

    public static void test5() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(500);
            return Thread.currentThread().getName();
        });
        executor.submit(futureTask);
        System.out.println(futureTask.get());
        executor.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test5();
    }
}
