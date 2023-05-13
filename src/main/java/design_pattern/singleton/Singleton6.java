package design_pattern.singleton;

/**
 * Lazy Mode
 * Double-Checked Lock (thread-unsafe)
 *
 * @date 2022-10-14
 **/
public class Singleton6 {

    private static volatile Singleton6 instance;

    private Singleton6() {
    }

    public static synchronized Singleton6 getInstance() {
        if (instance == null) {
            synchronized (Singleton6.class) {
                if (instance == null) {
                    return new Singleton6();
                }
            }
        }
        return instance;
    }
}
