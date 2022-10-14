package concurrent4.print_order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintOrder3 {
    private static volatile int flag = 1;

    public static void first() {
        while (flag != 1) {
            Thread.yield();
        }
        log.info("first");
        flag = 2;
    }

    public static void second() {
        while (flag != 2) {
            Thread.yield();
        }
        log.info("second");
        flag = 3;
    }

    public static void third() {
        while (flag != 3) {
            Thread.yield();
        }
        log.info("third");
    }

    public static void main(String[] args) {
        new Thread(PrintOrder3::first).start();
        new Thread(PrintOrder3::second).start();
        new Thread(PrintOrder3::third).start();
    }
}