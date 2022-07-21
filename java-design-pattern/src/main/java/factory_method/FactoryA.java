package factory_method;

import factory_simple.Product;
import factory_simple.ProductA;

public class FactoryA implements Factory {

    @Override
    public Product createProduct() {
        return new ProductA();
    }
}
