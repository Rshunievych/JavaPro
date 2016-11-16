package com.courses.homework;

import java.io.FileOutputStream;
import java.io.IOException;

@SaveTo(path = "file.txt")
public class TextContainer {

    private String text;

    public TextContainer(String text) {
        this.text = text;
    }

    @SaverAnnotation
    public void save(String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}