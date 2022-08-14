package basic;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @date 2022-7-21
 **/
public class BasicTest {

    @Test
    public void test1() {
        System.out.println(0.1 + 0.2);
        System.out.println(1.0 - 0.8);
        System.out.println(2.013 * 100);
        System.out.println(123.3 / 100);
        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
    }

    @Test
    public void test2() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";               // constant pool
        String s4 = "a" + "b";          // constant pool
        String s5 = s1 + s2;            // heap
        String s6 = new String("ab");    // heap

        System.out.println(s3 == s4);   // true
        System.out.println(s3 == s5);   // false
        System.out.println(s3 == s6);   // false

        System.out.println(s4 == s5);   // false
        System.out.println(s4 == s6);   // false

        System.out.println(s5 == s6);   // false
    }

    @Test
    public void test3() {
        final String s1 = "a";
        final String s2 = "b";
        String s3 = "ab";               // constant pool
        String s4 = "a" + "b";          // constant pool
        String s5 = s1 + s2;            // constant pool

        System.out.println(s3 == s5);   // true
        System.out.println(s4 == s5);   // true
    }

    public void test4() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, 0, 1000);
    }
}
