package concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Slf4j
public class CompletableFutureTest1 {

    public static void test1() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(x -> x + "World")
                .thenAccept(log::info)
                .thenRun(() -> log.info("thenRun"));
    }

    public static void test2() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(x -> x + "World")
                .whenComplete((res, err) -> {
                    log.info(res);
                    log.error("{}", err);
                });
    }

    public static void test3() {
        CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return "Hello";
        }).exceptionally(err -> {
            log.error("{}", err.getMessage());
            return "exceptionally";
        }).whenComplete((res, err) -> {
            log.info(res);
            log.error("{}", err);
        });
    }

    public static void test4() {
        CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return "Hello";
        }).handle((res, err) -> {
            log.info(res);
            log.error("{}", err.getMessage());
            return "handle";
        }).whenComplete((res, err) -> {
            log.info(res);
            log.error("{}", err);
        });
    }

    public static void test5() {
        CompletableFuture.supplyAsync(() -> "hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "compose"))
                .thenAccept(log::info);

        CompletableFuture.supplyAsync(() -> "hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> "combine"), (x, y) -> x + y)
                .thenAccept(log::info);
    }

    public static void test6() {
        CompletableFuture[] futures = IntStream.range(0, 4)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> "result" + i))
                .map(f -> f.thenAccept(log::info))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();
    }

    public static void main(String[] args) {
        test6();
    }
}
