package concurrent.atomic;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicTest3 {
    static class Account {
        private AtomicReference<BigDecimal> balance;

        Account(BigDecimal balance) {
            this.balance = new AtomicReference<>(balance);
        }

        public void withdraw(BigDecimal amount) {
            //while (true) {
            //    BigDecimal prev = balance.get();
            //    BigDecimal next = prev.subtract(amount);
            //    if (balance.compareAndSet(prev, next)) {
            //        break;
            //    }
            //}
            balance.getAndSet(balance.get().subtract(amount));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(new BigDecimal("10000"));
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                account.withdraw(new BigDecimal("10"));
            });
            t.start();
            t.join();
        }
    }
}
