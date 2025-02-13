package basic;


import org.junit.Test;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class CalculatorTest {

    @Test
    public void test() {
        String s = "11+20+30";
        LinkedList<String> strs = infix2postfix(s);
        System.out.println(strs);
        System.out.println(postfix2Value(strs));
    }

    public static LinkedList<String> infix2postfix(String s) {
        LinkedList<String> result = new LinkedList<>();
        LinkedList<String> stack = new LinkedList<>();
        StringTokenizer tokenizer = new StringTokenizer(s, "()+-*/", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.trim().length() == 0) {
            } else if (!isOpreator(token)) {
                result.addLast(token);
            } else if ("(".equals(token)) {
                stack.addLast(token);
            } else if (")".equals(token)) {
                while (stack.size() != 0 && stack.peekLast().charAt(0) != '(') {
                    result.addLast(stack.pollLast());
                }
                stack.pollLast();
            } else if ("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token)) {
                if (stack.size() == 0 || ")".equals(stack.peekLast()) || prior(token) > prior(stack.peekLast())) {
                    stack.addLast(token);
                } else {
                    while (stack.size() != 0 && prior(token) <= prior(stack.peekLast())) {
                        result.addLast(stack.pollLast());
                    }
                    stack.addLast(token);
                }
            }
        }

        while (stack.size() != 0) {
            result.addLast(stack.pollLast());
        }
        return result;
    }

    public static int postfix2Value(LinkedList<String> postfix) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (String token : postfix) {
            if (!isOpreator(token)) {
                stack.addLast(Integer.parseInt(token));
            } else {
                int num2 = stack.pollLast();
                int num1 = stack.pollLast();
                switch (token.charAt(0)) {
                    case '+':
                        stack.addLast(num1 + num2);
                        break;
                    case '-':
                        stack.addLast(num1 - num2);
                        break;
                    case '*':
                        stack.addLast(num1 * num2);
                        break;
                    case '/':
                        stack.addLast(num1 / num2);
                        break;
                    default:
                        break;
                }
            }
        }
        return stack.pollLast();
    }

    private static boolean isOpreator(String token) {
        return "+".equals(token) ||
                "-".equals(token) ||
                "*".equals(token) ||
                "/".equals(token) ||
                "(".equals(token) ||
                ")".equals(token);
    }

    private static int prior(String token) {
        if ("+".equals(token) || "-".equals(token))
            return 1;
        if ("*".equals(token) || "/".equals(token))
            return 2;
        return 0;
    }
}
