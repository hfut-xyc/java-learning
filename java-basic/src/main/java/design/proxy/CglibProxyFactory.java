package design.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory {

    private Object target;
    private MethodInterceptor interceptor;

    public CglibProxyFactory(Object target){
        this.target = target;
        this.interceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("begin transaction");
                Object result = method.invoke(target, args);
                System.out.println("commit transaction");
                return result;
            }
        };
    }

    public Object getProxyInstance(){
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(interceptor);
        return en.create();
    }

}
