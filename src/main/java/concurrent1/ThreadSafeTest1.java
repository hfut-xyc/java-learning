package concurrent1;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class ThreadSafeTest1 {
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
    }
    public static void main(String[] args) throws InterruptedException {
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
}

