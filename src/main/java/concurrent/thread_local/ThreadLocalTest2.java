package concurrent.thread_local;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class ThreadLocalTest2 {

    public static void test0() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    log.info("{}", sdf.parse("2022-09-10"));
                } catch (ParseException e) {
                    log.error("{}", e.getMessage());
                }
            }).start();
        }
    }

    public static void test1() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    log.info("{}", sdf.parse("2022-09-10"));
                } catch (ParseException e) {
                    log.error("{}", e.getMessage());
                }
            }).start();
        }
    }

    public static void test2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    synchronized (sdf) {
                        log.info("{}", sdf.parse("2022-09-10"));
                    }
                } catch (ParseException e) {
                    log.error("{}", e.getMessage());
                }
            }).start();
        }
    }

    public static void test3() {
        ThreadLocal<SimpleDateFormat> threadLocal =
                ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    log.info("{}", threadLocal.get().parse("2022-09-10"));
                } catch (ParseException e) {
                    log.error("{}", e.getMessage());
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        test2();
    }

}


