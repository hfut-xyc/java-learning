package thread;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadBasic {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void test1() {
        Thread t1 = new MyThread();

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

    public static void main(String[] args) {

    }
}
