package concurrent3.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class AtomicArrayTest {

    private static AtomicIntegerArray atomicArray = new AtomicIntegerArray(5);

    public static void main(String[] args) throws InterruptedException {
        int length = atomicArray.length();
        for (int i = 0; i < length; i++) {
            Thread t = new Thread(() -> {
                IntStream.range(0, 10000).forEach(j -> {
                    atomicArray.getAndIncrement(j % length);
                });
            });
            t.start();
            t.join();
        }

        log.info("{}", atomicArray);
    }
}
