package concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class FutureTest2 {

    public static void test1() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(x -> x + "World")
                .thenAccept(log::info)
                .thenRun(() -> log.info("done!"));
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
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "world"))
                .thenAccept(log::info);

        CompletableFuture.supplyAsync(() -> "hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> "world"), (x, y) -> x + y)
                .thenAccept(log::info);
    }

    public static void main(String[] args) {
        test3();
    }
}
