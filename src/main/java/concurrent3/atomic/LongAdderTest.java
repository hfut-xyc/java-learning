package concurrent3.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class LongAdderTest {

    public static void main(String[] args) throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                IntStream.range(0, 1000).forEach(j -> {
                    atomicLong.getAndIncrement();
                });
            });
            t.start();
            t.join();
        }
        log.info("{}", atomicLong);
        log.info("{}", System.currentTimeMillis() - start1);

        LongAdder longAdder = new LongAdder();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                IntStream.range(0, 1000).forEach(j -> {
                    longAdder.increment();
                });
            });
            t.start();
            t.join();
        }
        log.info("{}", longAdder);
        log.info("{}", System.currentTimeMillis() - start2);
    }
}
