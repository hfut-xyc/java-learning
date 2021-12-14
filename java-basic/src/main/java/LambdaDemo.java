import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaDemo {

    public static void main(String[] args) {
        List<String> list = Stream.of("alpha", "beta", "grammar", "delta")
                .collect(Collectors.toList());

        // 1.Consumer<T>: T -> void
        Consumer<String> c = System.out::println;
        list.forEach(c);

        // 2.Predicate<T>: T -> boolean
        Predicate<String> p = x -> x.length() > 4;
        list.removeIf(p);

        // 3.Supplier<T>: () -> T
        Supplier<Thread> s = Thread::new;
        Thread t1 = s.get();

        // 4.Function<T, R>: T -> R
        Function<String, Thread> f = Thread::new;
        Thread t2 = f.apply("t2");
    }
}
