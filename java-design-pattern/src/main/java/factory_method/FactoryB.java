package factory_method;

import factory_simple.Product;
import factory_simple.ProductB;

public class FactoryB implements Factory {
    
    @Override
    public Product createProduct() {
        return new ProductB();
    }
}
