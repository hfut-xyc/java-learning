package design_pattern.singleton;

/**
 * Hungry Mode:
 * static code block (thread-safe)
 *
 * @date 2022-10-14
 **/
public class Singleton2 {

    private final static Singleton2 instance;

    static {
        instance = new Singleton2();
    }

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return instance;
    }
}
