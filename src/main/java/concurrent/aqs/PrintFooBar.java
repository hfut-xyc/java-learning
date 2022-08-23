package concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class PrintFooBar {

    private static final int n = 5;
    private static final Semaphore foo = new Semaphore(1);
    private static final Semaphore bar = new Semaphore(0);

    public static void foo() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            foo.acquire();
            log.info("foo");
            bar.release();
        }
    }

    public static void bar() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
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