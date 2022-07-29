package design_pattern.factory_method;

import design_pattern.factory_simple.Product;
import design_pattern.factory_simple.ProductA;

public class FactoryA implements Factory {

    @Override
    public Product createProduct() {
        return new ProductA();
    }
}
