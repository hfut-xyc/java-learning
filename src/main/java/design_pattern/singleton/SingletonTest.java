package design_pattern.singleton;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.stream.IntStream;

/**
 * @date 2022-10-14
 **/
@Slf4j
public class SingletonTest {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                log.info(Singleton7.getInstance().toString());
            }).start();
        });
    }

    @SneakyThrows
    private static void test2() {
        Constructor<Singleton7> constructor = Singleton7.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        System.out.println(Singleton7.getInstance());
        System.out.println(constructor.newInstance());
    }

    @SneakyThrows
    private static void test3() {
        Constructor<Singleton8> constructor = Singleton8.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        System.out.println(Singleton8.INSTANCE);
        System.out.println(constructor.newInstance());
    }
}
