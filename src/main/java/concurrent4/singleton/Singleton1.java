package concurrent4.singleton;

/**
 * Hungry Mode
 * static constant (thread-safe)
 *
 * @date 2022-10-14
 **/
public class Singleton1 {

    private static final Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}
