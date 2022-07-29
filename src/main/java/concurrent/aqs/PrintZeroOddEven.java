package concurrent.aqs;

import java.util.concurrent.Semaphore;

public class PrintZeroOddEven {
    private static int n = 5;
    private static volatile int count = 1;
    private static Semaphore zero = new Semaphore(1);
    private static Semaphore odd = new Semaphore(0);
    private static Semaphore even = new Semaphore(0);

    public static void zero() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zero.acquire();
            System.out.print(0);
            if (i % 2 == 1) {
                odd.release();
            } else {
                even.release();
            }
        }
    }

    public static void odd() throws InterruptedException {
        while (count <= n) {
            odd.acquire();
            if (count % 2 != 0) {
                System.out.print(count);
                zero.release();
            }
            count++;
        }
    }

    public static void even() throws InterruptedException {
        while (count <= n) {
            even.acquire();
            if (count % 2 == 0) {
                System.out.print(count);
                zero.release();
            }
            count++;
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                zero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                odd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                even();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }

}
