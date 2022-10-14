package concurrent4.singleton;

/**
 * Lazy Mode:
 * (thread-unsafe)
 *
 * @date 2022-10-14
 **/
public class Singleton3 {

    private static Singleton3 instance;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (instance == null) {
            return new Singleton3();
        }
        return instance;
    }
}
