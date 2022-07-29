package design_pattern.factory_method;

import design_pattern.factory_simple.Product;

public class FactoryMethod {

    public static void main(String[] args) {
        Factory factoryA = new FactoryA();
        Product productA = factoryA.createProduct();

        Factory factoryB = new FactoryA();
        Product productB = factoryB.createProduct();
    }
}
