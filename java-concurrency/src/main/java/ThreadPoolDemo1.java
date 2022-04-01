import java.util.concurrent.*;

public class ThreadPoolDemo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName());
        });
    }

}
