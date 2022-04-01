
import java.util.concurrent.*;

public class FutureDemo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Callable<String> callable = () -> Thread.currentThread().getName();
        Future<String> future = executor.submit(callable);
        String res = future.get();
        System.out.println(res);

        FutureTask<String> futureTask = new FutureTask<>(callable);
        executor.submit(callable);
        System.out.println(futureTask.get());


    }
}
