package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FutureDemo2 {

    public static void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 1 / 0;
            return "HelloWorld";
        });
        future.thenAccept(res -> {
            System.out.println(res);
        }).exceptionally(err -> {
            System.out.println(err.getMessage());
            return null;
        });
        future.get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }
}
