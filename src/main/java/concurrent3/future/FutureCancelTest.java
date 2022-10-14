package concurrent3.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @date 2022-10-14
 **/
@Slf4j
public class FutureCancelTest {
    public static void test1() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("finish");
            } catch (InterruptedException e) {
                log.error(e.toString());
            }
            return "hello";
        });

        log.info("succeed to cancel? {}", future.cancel(true));

        executor.shutdown();
    }

    public static void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Future<String> future = executor.submit(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("finish");
            } catch (InterruptedException e) {
                log.error(e.toString());
            }
            return "hello";
        });

        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error(e.toString());
        }
        log.info("succeed to cancel? {}", future.cancel(true));
        executor.shutdown();
    }

    public static void main(String[] args) {
        test1();
    }

}
