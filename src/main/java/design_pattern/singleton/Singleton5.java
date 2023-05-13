package design_pattern.singleton;

/**
 * Lazy Mode
 * thread-unsafe
 * @date 2022-10-14
 **/
public class Singleton5 {

    private static Singleton5 instance;

    private Singleton5() {
    }

    public static synchronized Singleton5 getInstance() {
        if (instance == null) {
            synchronized (Singleton5.class) {
                return new Singleton5();
            }
        }
        return instance;
    }
}
