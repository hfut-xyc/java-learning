package design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    private Object target;
    private InvocationHandler interceptor;

    public JdkProxyFactory(Object target) {
        this.target = target;
        this.interceptor = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("begin transaction");
                Object result = method.invoke(target, args);
                System.out.println("commit transaction");
                return result;
            }
        };
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                interceptor);
    }
}
