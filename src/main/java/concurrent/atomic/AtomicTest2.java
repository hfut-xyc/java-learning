package concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AtomicTest2 {

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(30000);
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                account.withdraw(10);
            });
            t.start();
            t.join();
        }
        log.info("balance={}", account.balance);
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
}
