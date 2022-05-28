package com.basic;


import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaDemo {

    private static class Apple {

        private String color;
        private Integer weight;

        Apple() {}

        Apple(String color) {
            this.color = color;
        }

        Apple(String color, Integer weight) {
            this.color = color;
            this.weight = weight;
        }
    }

    public static void lambda_demo1() {
        List<String> list = Stream.of("alpha", "beta", "grammar", "delta")
                .collect(Collectors.toList());

        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        list.sort((x, y) -> x.length() - y.length());

        list.sort(Comparator.comparing(x -> x.length()));

        list.sort(Comparator.comparing(String::length));
    }

    public static void lambda_demo2() {
        List<String> list = Stream.of("alpha", "beta", "grammar", "delta")
                .collect(Collectors.toList());

        // Consumer<T>: T -> void
        Consumer<String> c = System.out::println;
        list.forEach(c);

        // Predicate<T>: T -> boolean
        Predicate<String> p = x -> x.length() > 4;
        list.removeIf(p);

        // Supplier<T>: () -> T
        Supplier<Apple> s = Apple::new;
        Apple a1 = s.get();

        // Function<T, R>: T -> R
        Function<String, Apple> f = Apple::new;
        Apple a2 = f.apply("red");

        // BiFunction<T, U, R>: (T, U) -> R
        BiFunction<String, Integer, Apple> bf = Apple::new;
        Apple a3 = bf.apply("red", 100);
    }


    public static void main(String[] args) {

    }
}
