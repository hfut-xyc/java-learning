package concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class LongAccumulatorTest {
    public static void main(String[] args) throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> 2 * x, 1);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                accumulator.accumulate(2);
            });
            t.start();
            t.join();
        }
        log.info("{}", accumulator);
    }
}
