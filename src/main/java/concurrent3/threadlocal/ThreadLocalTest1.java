package concurrent3.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalTest1 {

    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    threadLocal.set(threadLocal.get() + 1);
                    log.info("{}", threadLocal.get());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }
            }).start();
        }
    }

}


