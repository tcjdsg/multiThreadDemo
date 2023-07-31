package proxy;

import java.lang.reflect.*;

interface SmsService {
    String send(String message);
}
class SmsServiceImpl implements SmsService {
    private String value = "";
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}

/**
 * @author shuang.kou
 * @createTime 2020年05月11日 11:23:00
 */
class DebugInvocationHandler implements InvocationHandler {
    /**
     * 代理类中的真实对象
     */
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("before method " + method.getName());//调用方法之前，我们可以添加自己的操作
        Object result = method.invoke(target, args);
        System.out.println("after method " + method.getName());//调用方法之后，我们同样可以添加自己的操作
        return result;
    }
}

public class JDKproxy {
    public static void other() throws Exception {
        Class<?> proxyClass = Proxy.getProxyClass(JDKproxy.class.getClassLoader(), SmsService.class);
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        InvocationHandler workHandler = new DebugInvocationHandler(new SmsServiceImpl());
        SmsService worker = (SmsService) constructor.newInstance(workHandler);
        worker.send("hhhhhhhhhhhhhh");
    }

    public static Object getProxy(Object target) {
        Object o = Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // 目标类的类加载
                target.getClass().getInterfaces(),  // 代理需要实现的接口，可指定多个
                new DebugInvocationHandler(target)   // 代理对象对应的自定义 InvocationHandler
        );
        return o;
    }

    public static void main(String[] args) throws Exception {
        SmsService smsService = (SmsService) JDKproxy.getProxy(new SmsServiceImpl());
        smsService.send("java");
        JDKproxy.other();
    }
}
