package concurrent4.singleton;

/**
 * Lazy Mode:
 * static inner class (thread-safe)
 *
 * @date 2022-10-14
 **/
public class Singleton7 {

    private Singleton7() {
    }

    private static class Singleton {
        private static final Singleton7 instance = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return Singleton.instance;
    }
}
