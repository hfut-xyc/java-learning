package design_pattern.singleton;

/**
 * Lazy Mode
 * using synchronized, thread-safe
 *
 * @date 2022-10-14
 **/
public class Singleton4 {

    private static Singleton4 instance;

    private Singleton4() {
    }

    public static synchronized Singleton4 getInstance() {
        if (instance == null) {
            return new Singleton4();
        }
        return instance;
    }
}
