package concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AtomicTest1 {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);

        log.info("{}", count.incrementAndGet());

        log.info("{}", count.decrementAndGet());

        log.info("{}", count.addAndGet(10));

        log.info("{}", count.updateAndGet(x -> x * 10));

    }
}
