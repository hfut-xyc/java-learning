package design_pattern.factory_method;

import design_pattern.factory_simple.Product;
import design_pattern.factory_simple.ProductB;

public class FactoryB implements Factory {
    
    @Override
    public Product createProduct() {
        return new ProductB();
    }
}
