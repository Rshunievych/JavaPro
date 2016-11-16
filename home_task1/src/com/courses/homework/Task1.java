package com.courses.homework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Task1 {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        Class<?> clss = TestClass.class;
        for (Method method : clss.getMethods()) {
            if (method.isAnnotationPresent(TestAnnotation.class)) {
                TestClass myClass = new TestClass();
                TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
                method.invoke(myClass, testAnnotation.a(), testAnnotation.b());
            }
        }
    }
}
