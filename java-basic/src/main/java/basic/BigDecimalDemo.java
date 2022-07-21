package basic;

import java.math.BigDecimal;

/**
 * @date 2022-7-21
 **/
public class BigDecimalDemo {
    public static void main(String[] args) {
        System.out.println(0.1 + 0.2);
        System.out.println(1.0 - 0.8);
        System.out.println(2.013 * 100);
        System.out.println(123.3 / 100);

        System.out.println(new BigDecimal(0.1).add(new BigDecimal(0.2)));
    }

}
