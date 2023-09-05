package concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureGetTest {

    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "hello";
        });
        log.info("{}", future.isDone());
        future.get();
        log.info("{}", future.isDone());

        executor.shutdown();
    }

    public static void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            int i = 1 / 0;
            return "hello";
        });
        try {
            Thread.sleep(1000);
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.toString());
        }
        executor.shutdown();
    }

    public static void test3() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            try {
                Thread.sleep(3000);
                log.info("finish");
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            return "hello";
        });

        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            log.error(e.toString());
        }
        executor.shutdown();
    }

    public static void main(String[] args) throws Exception {
        test3();
    }

}
