package com.creatingpdf;


import java.io.*;
import java.nio.file.Paths;

public class Service {
    public String readPdf() {
        StringBuilder file = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\user\\Desktop\\10.pdf"))) {
            String row;
            while ((row = bufferedReader.readLine()) != null){
                file.append(row);
                System.out.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(file);
    }
}
