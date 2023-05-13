package concurrent3.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AtomicIntegerTest {

    public static void test1() {
        AtomicInteger count = new AtomicInteger(0);
        log.info("{}", count.incrementAndGet());
        log.info("{}", count.decrementAndGet());
        log.info("{}", count.addAndGet(10));
        log.info("{}", count.updateAndGet(x -> x * 10));
    }

    static class Account {
        private AtomicInteger balance;

        Account(int balance) {
            this.balance = new AtomicInteger(balance);
        }

        public void withdraw(int amount) {
            //while (true) {
            //    int prev = balance.get();
            //    int next = prev - amount;
            //    if (balance.compareAndSet(prev, next)) {
            //        break;
            //    }
            //}
            if (balance.get() >= amount) {
                balance.getAndAdd(-amount);
            }
        }
    }

    public static void test2() throws InterruptedException {
        Account account = new Account(1000);
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                account.withdraw(10);
            });
            t.start();
            t.join();
        }
        log.info("{}", account.balance);
    }

    public static void main(String[] args) throws InterruptedException {
        test2();
    }
}
