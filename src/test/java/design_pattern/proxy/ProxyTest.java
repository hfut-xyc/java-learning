package design_pattern.proxy;

import org.junit.Test;

public class ProxyTest {

    @Test
    public void test1() {
        UserService userService = new UserServiceImpl();
        JdkProxyFactory jdkProxyFactory = new JdkProxyFactory(userService);
        UserService userProxy = (UserService) jdkProxyFactory.getProxyInstance();
        userProxy.update();
    }

    @Test
    public void test2() {
        ProductService productService = new ProductService();
        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory(productService);
        ProductService productProxy = (ProductService) cglibProxyFactory.getProxyInstance();
        productProxy.update();
    }

}
