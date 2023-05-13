package concurrent3.atomic;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AtomicReferenceTest {

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
        Account account = new Account(new BigDecimal("1000"));
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                account.withdraw(new BigDecimal("10"));
            });
            t.start();
            t.join();
        }
        log.info("{}", account.balance);
    }
}
