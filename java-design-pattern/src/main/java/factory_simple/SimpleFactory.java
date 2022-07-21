package factory_simple;

public class SimpleFactory {

    public static Product create(String name) {
        if (name.equalsIgnoreCase("A"))
            return new ProductA();
        else if (name.equalsIgnoreCase("B"))
            return new ProductB();
        return null;
    }
}

