package concurrent.aqs;

import java.util.concurrent.Semaphore;

public class PrintFooBar {

    private static int n = 5;
    private static Semaphore foo = new Semaphore(1);
    private static Semaphore bar = new Semaphore(0);

    public static void foo() {
        for (int i = 1; i <= n; i++) {
            try {
                foo.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("foo");
            bar.release();
        }
    }

    public static void bar() {
        for (int i = 1; i <= n; i++) {
            try {
                bar.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("bar");
            foo.release();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(PrintFooBar::foo);
        Thread t2 = new Thread(PrintFooBar::bar);
        t1.start();
        t2.start();
    }
}