package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo2 {

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            }
        }
    }

    public static void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
        Thread.sleep(1000);
        System.out.println(executorService.isShutdown());
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        executorService.execute(new Task());
    }

    public static void test2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
        Thread.sleep(1000);
        executorService.shutdown();
        System.out.println(executorService.isTerminated());
        Thread.sleep(9000);
        System.out.println(executorService.isTerminated());
    }

    public static void test3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
        Thread.sleep(1000);
        executorService.shutdown();
        boolean flag = executorService.awaitTermination(6, TimeUnit.SECONDS);
        System.out.println(flag);
    }

    public static void test4() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
        Thread.sleep(1000);
        executorService.shutdownNow();
        Thread.sleep(3000);
        System.out.println(executorService.isTerminated());
    }

    public static void main(String[] args) throws InterruptedException {
        test4();
    }
}
