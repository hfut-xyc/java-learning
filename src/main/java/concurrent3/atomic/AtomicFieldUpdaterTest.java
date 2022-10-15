package concurrent3.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @date 2022-10-15
 **/
@Slf4j
public class AtomicFieldUpdaterTest {

    static class Account {
        volatile int balance;
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();
        AtomicIntegerFieldUpdater<Account> fieldUpdater =
                AtomicIntegerFieldUpdater.newUpdater(Account.class, "balance");

        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(() -> fieldUpdater.getAndIncrement(account));
            t.start();
            t.join();
        }

        log.info("{}", account.balance);
    }
}
