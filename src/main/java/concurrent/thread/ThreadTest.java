package concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class ThreadTest {

    public void test() {

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, 0, 1000);
    }

}
