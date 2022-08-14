package concurrent.future;

import java.util.concurrent.CompletableFuture;

public class FutureTest2 {

    public static void test1() {
        CompletableFuture.supplyAsync(() -> "Hello").thenApply(x -> x + "World")
                .thenAccept(System.out::println)
                .thenRun(() -> System.out.println("done!"));
    }

    public static void test2() {
        CompletableFuture.supplyAsync(() -> "Hello").thenApply(x -> x + "World")
                .whenComplete((res, err) -> {
                    System.out.println(res);
                    System.out.println(err);
                });
    }

    public static void test3() {
        CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return "Hello";
        }).exceptionally(err -> {
            System.out.println(err);
            return "exceptionally";
        }).whenComplete((res, err) -> {
            System.out.println(res);
            System.out.println(err);
        });
    }

    public static void test4() {
        CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return "Hello";
        }).handle((res, err) -> {
            System.out.println(res);
            System.out.println(err);
            return "handle";
        }).whenComplete((res, err) -> {
            System.out.println(res);
            System.out.println(err);
        });
    }

    public static void main(String[] args) throws Exception {
        test3();
    }
}
