package com.example.myapplication.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * InvocationHandler
 * 日志处理器，动态代理类，实现InvocationHandler接口
 */
public class LoggerInterceptor implements InvocationHandler {
    private Object target;//目标对象的引用，这里设计成Object类型，更具通用性

    LoggerInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arg) throws Throwable {
        System.out.println("Entered " + target.getClass().getName() + "-" + method.getName() + ",with arguments{" + arg[0] + "}");
        Object result = method.invoke(target, arg);//调用目标对象的方法
        System.out.println("Before return:" + result);
        return result;
    }
}

