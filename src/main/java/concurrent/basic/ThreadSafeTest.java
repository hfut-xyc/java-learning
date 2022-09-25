package concurrent.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Slf4j
public class ThreadSafeTest {
    static class Account {
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        public void withdraw(int amount) {
            synchronized (this) {
                if (balance >= amount) {
                    balance -= amount;
                }
            }
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

    public static void test1() throws InterruptedException {
        Account account = new Account(1000);
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                account.withdraw(10);
            });
            threadList.add(t);
            t.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
        log.info("account = {}", account.balance);
    }


    public static void test2() throws InterruptedException {
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

    public static void main(String[] args) throws InterruptedException {
        test1();
    }
}

