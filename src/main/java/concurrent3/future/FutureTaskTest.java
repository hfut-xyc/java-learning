package concurrent3.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @date 2022-10-13
 **/
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(500);
            return "hello";
        });
        executor.submit(futureTask);
        System.out.println(futureTask.get());
        executor.shutdown();
    }
}
