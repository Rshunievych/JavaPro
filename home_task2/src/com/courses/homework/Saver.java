package com.courses.homework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Saver {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException{

        TextContainer textContainer = new TextContainer("Text to save");

        Class<?> clss = textContainer.getClass();
        if (clss.isAnnotationPresent(SaveTo.class)) {
            SaveTo saveToAnnotation = clss.getAnnotation(SaveTo.class);

            for (Method method : clss.getMethods()) {
                if (method.isAnnotationPresent(SaverAnnotation.class)) {
                    method.invoke(textContainer, saveToAnnotation.path());
                }
            }
        }
    }
}
