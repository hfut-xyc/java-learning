package concurrent4.producer_consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

/**
 * @date 2022-10-14
 **/
@Slf4j
public class ProducerConsumerTest3 {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        IntStream.rangeClosed(1, 4).forEach(i -> {
            new Thread(() -> {
                try {
                    queue.put("msg");
                    log.info("put, size={}", queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Producer-" + i).start();
        });

        IntStream.rangeClosed(1, 3).forEach(i -> {
            new Thread(() -> {
                try {
                    queue.take();
                    log.info("take, size={}", queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Consumer-" + i).start();
        });
    }
}
