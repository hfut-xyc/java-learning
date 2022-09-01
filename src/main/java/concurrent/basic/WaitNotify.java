package concurrent.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitNotify {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            synchronized (lock) {
                log.info("start");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("end");
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();

        Thread.sleep(2000);
        log.info("notify");
        synchronized (lock) {
            //obj.notify();
            lock.notifyAll();
        }
    }
}
