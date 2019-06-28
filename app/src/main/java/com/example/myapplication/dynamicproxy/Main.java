package com.example.myapplication.dynamicproxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        AppService target = new SubClass();//生成目标对象,动态代理只能代理直接接口
        //接下来创建代理对象
        AppService proxy = (AppService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new LoggerInterceptor(target));
        proxy.createApp("Kevin Test");
    }
}

