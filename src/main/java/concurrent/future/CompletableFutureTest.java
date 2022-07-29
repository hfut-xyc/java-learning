package concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureTest {

    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            int i = 1 / 0;
            return "Hello";
        });

        future.thenAccept(res -> {
            log.info(res);
        }).exceptionally(err -> {
            log.error(err.getMessage());
            return null;
        });
        future.get();
    }
}
