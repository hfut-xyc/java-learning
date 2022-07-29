package concurrent.aqs;

import java.util.concurrent.CountDownLatch;

public class PrintInOrder2 {

    private static CountDownLatch second = new CountDownLatch(1);
    private static CountDownLatch third = new CountDownLatch(1);

    public static void first()  {
        System.out.println("first");
        second.countDown();
    }

    public static void second()  {
        try {
            second.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("second");
        third.countDown();
    }

    public static void third()  {
        try {
            third.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("third");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(PrintInOrder2::first);
        Thread t2 = new Thread(PrintInOrder2::second);
        Thread t3 = new Thread(PrintInOrder2::third);
        t1.start();
        t2.start();
        t3.start();
    }
}
