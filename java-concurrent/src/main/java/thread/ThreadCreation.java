package thread;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadCreation {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void create_thread1() {
        // subclass of Thread
        Thread t1 = new MyThread();

        // anonymous inner class
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        // lambda expression
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
    }

    public void create_thread2() {
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
