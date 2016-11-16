package com.courses.homework;

public class TestClass {

    @TestAnnotation(a = 4, b =5)
    public void test(int a, int b){
        System.out.println("a = " + a + ", b = " + b);
    }
}
