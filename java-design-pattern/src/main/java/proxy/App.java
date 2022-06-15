package proxy;

public class App {

    public static void test1() {
        UserService userService = new UserServiceImpl();
        JdkProxyFactory jdkProxyFactory = new JdkProxyFactory(userService);
        UserService userProxy = (UserService) jdkProxyFactory.getProxyInstance();
        userProxy.update();
    }

    public static void test2() {
        ProductService productService = new ProductService();
        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory(productService);
        ProductService productProxy = (ProductService) cglibProxyFactory.getProxyInstance();
        productProxy.update();
    }

    public static void main(String[] args) {
        test1();
    }
}
