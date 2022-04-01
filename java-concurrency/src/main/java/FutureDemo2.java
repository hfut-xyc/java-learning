import java.util.concurrent.CompletableFuture;

public class FutureDemo2 {

    CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello");
}
