package concurrent.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @date 2022-9-25
 * 两阶段终止模式
 **/
@Slf4j
public class ThreadInterruptTest2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000); // 情况1
                    log.debug("执行监控记录");  // 情况2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 如果是情况1，需要重新设置打断标记
                    current.interrupt();
                }
            }
        });

        thread.start();
        Thread.sleep(3500);
        thread.interrupt();
    }
}
