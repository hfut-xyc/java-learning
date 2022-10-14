package concurrent1;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;


@Slf4j
public class ThreadSafeTest2 {
    static class Account {
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        public void transfer(Account target, int amount) {
            synchronized (Account.class) {
                if (this.balance >= amount) {
                    this.balance -= amount;
                    target.balance += amount;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account1.transfer(account2, random.nextInt(100) + 1);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account2.transfer(account1, random.nextInt(100) + 1);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.info("account1 = {}", account1.balance);
        log.info("account2 = {}", account2.balance);
    }
}

