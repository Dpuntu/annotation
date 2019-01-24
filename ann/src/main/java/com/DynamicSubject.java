package com;

import com.ann.PrintContent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: fangmingxing
 * @Date: 2019-01-22 00:12
 */
class DynamicSubject implements InvocationHandler {
    private Object object;

    DynamicSubject(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(PrintContent.class)) {
            PrintContent printContent = method.getAnnotation(PrintContent.class);
            System.out.println(String.format("----- 调用 %s 之前 -----", method.getName()));
            method.invoke(object, printContent.value());
            System.out.println(String.format("----- 调用 %s 之后 -----\n", method.getName()));
            return proxy;
        }
        return null;
    }
}
