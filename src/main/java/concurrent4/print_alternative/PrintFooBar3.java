package concurrent4.print_alternative;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class PrintFooBar3 {
    private static final Semaphore foo = new Semaphore(1);
    private static final Semaphore bar = new Semaphore(0);
    private static final int loopCount = 5;

    public static void foo() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            foo.acquire();
            log.info("foo");
            bar.release();
        }
    }

    public static void bar() throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            bar.acquire();
            log.info("bar");
            foo.release();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                foo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                bar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}