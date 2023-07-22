package basic;

import org.junit.Test;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class Calculator {

    @Test
    public void test() {
        String s = "11+(20+30)*4";

        System.out.println(infix2postfix(s));
        System.out.println(postfix2Value(infix2postfix(s)));
    }

    public LinkedList<String> infix2postfix(String s) {
        LinkedList<String> result = new LinkedList<>();
        LinkedList<String> stack = new LinkedList<>();
        StringTokenizer tokenizer = new StringTokenizer(s, "()+-*/", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            char ch = token.charAt(0);
            if (token.trim().length() == 0) {
                continue;
            } else if (!isOpreator(ch)) {
                result.addLast(token);
            } else if (ch == '(') {
                stack.addLast(token);
            } else if (ch == ')') {
                while (stack.size() != 0 && stack.peekLast().charAt(0) != '(') {
                    result.addLast(stack.pollLast());
                }
                stack.pollLast();
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                if (stack.size() == 0 || stack.peekLast().charAt(0) == '(') {
                    stack.addLast(token);
                } else if (prior(ch) > prior(stack.peekLast().charAt(0))) {
                    stack.addLast(token);
                } else {
                    while (stack.size() != 0 && prior(ch) <= prior(stack.peekLast().charAt(0))) {
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


    private boolean isOpreator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == ')';
    }

    private int prior(char op) {
        if (op == '+' || op == '-')
            return 1;
        if (op == '*' || op == '/')
            return 2;
        return 0;
    }

    public int postfix2Value(LinkedList<String> postfix) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (String token : postfix) {
            char ch = token.charAt(0);

            if (!isOpreator(ch)) {
                stack.addLast(Integer.parseInt(token));
            } else {
                int num2 = stack.pollLast();
                int num1 = stack.pollLast();
                switch (ch) {
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

}
