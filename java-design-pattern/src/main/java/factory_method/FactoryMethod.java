package factory_method;

import factory_simple.Product;

public class FactoryMethod {

    public static void main(String[] args) {
        Factory factoryA = new FactoryA();
        Product productA = factoryA.createProduct();

        Factory factoryB = new FactoryA();
        Product productB = factoryB.createProduct();
    }
}
