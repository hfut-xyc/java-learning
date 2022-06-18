package aqs;

import java.util.concurrent.Semaphore;

public class PrintInOrder1 {

    private static Semaphore second = new Semaphore(0);
    private static Semaphore third = new Semaphore(0);

    public static void first()  {
        System.out.println("first");
        second.release();
    }

    public static void second()  {
        try {
            second.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("second");
        third.release();
    }

    public static void third()  {
        try {
            third.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("third");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(PrintInOrder1::first);
        Thread t2 = new Thread(PrintInOrder1::second);
        Thread t3 = new Thread(PrintInOrder1::third);
        t1.start();
        t2.start();
        t3.start();
    }
}
