package com;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    private final Object object;
    public ProxyHandler(Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoke method"  + method.getName());
        Object result = method.invoke(object, args);
        System.out.println("After invoke method" + method.getName());

        return result;
    }
}
