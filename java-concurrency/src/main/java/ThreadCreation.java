public class ThreadCreation {

    public static void main(String[] args) {
        // 方法1-继承Thread类
        Thread t1 = new MyThread();

        // 方法2-Lambda形式
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });

        // 方法2-匿名内部类形式
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
