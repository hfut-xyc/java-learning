package design_pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy {

    private Object target;
    private InvocationHandler interceptor;

    public JdkProxy(Object target) {
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


    interface UserService {
        void update();
    }

    static class UserServiceImpl implements UserService {
        @Override
        public void update() {
            System.out.println("user updated");
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        JdkProxy jdkProxy = new JdkProxy(userService);
        UserService userProxy = (UserService) jdkProxy.getProxyInstance();
        userProxy.update();
    }
}
