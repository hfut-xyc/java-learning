package basic;

import org.junit.Test;

/**
 * @date 2022-9-25
 **/
public class StringTest {

    @Test
    public void test1() {
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
    public void test2() {
        final String s1 = "a";
        final String s2 = "b";
        String s3 = "ab";               // constant pool
        String s4 = "a" + "b";          // constant pool
        String s5 = s1 + s2;            // constant pool

        System.out.println(s3 == s5);   // true
        System.out.println(s4 == s5);   // true
    }

    @Test
    public void test3() {
        String s1 = "ab";
        String s2 = new String("a") + new String("b");
        s2.intern();    // 入池失败
        System.out.println(s1 == s2); // false
    }

    @Test
    public void test4() {
        String s2 = new String("a") + new String("b");
        s2.intern();    // 入池成功
        String s1 = "ab";
        System.out.println(s1 == s2); // true
    }
}
