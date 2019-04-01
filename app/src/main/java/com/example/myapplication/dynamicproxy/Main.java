package com.example.myapplication.dynamicproxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        AppService target = new AppServiceImp();//生成目标对象
        //接下来创建代理对象
        AppService proxy = (AppService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new LoggerInterceptor(target));
        proxy.createApp("Kevin Test");
    }
}

