package com;

import com.ann.Autowired;

import java.lang.reflect.*;

public class Main {

    public static void main(String[] args) {
        Test test = new Test();
//        initObject(test);
//        test.student();
        test.asm();
    }

    private static void initObject(Object obj) {
        validateService(obj);
        inject(obj);
    }

    private static void validateService(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Object is null");
        }
    }

    private static void inject(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType() == Person.class) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    try {
                        Person student = new Student();
                        Class<?> cls = student.getClass();
                        Person person = (Person) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new DynamicSubject(student));
                        field.set(obj, person);
//                        field.set(obj, student);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

