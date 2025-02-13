package concurrent.application.print_order;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class PrintOrder6 {

    public static void main(String[] args) {
        Thread t3 = new Thread(() -> {
            LockSupport.park();
            log.info("third");
        });

        Thread t2 = new Thread(() -> {
            LockSupport.park();
            log.info("second");
            LockSupport.unpark(t3);
        });

        Thread t1 = new Thread(() -> {
            log.info("first");
            LockSupport.unpark(t2);
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
