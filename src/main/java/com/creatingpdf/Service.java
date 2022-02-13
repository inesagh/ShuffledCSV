package com.creatingpdf;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class Service {
    private static String basePath = "C:\\Users\\user\\Desktop\\";

    public static String readPdf(String fileName) {
        String encodedText = "";
        try (PDDocument pdDocument = PDDocument.load(new File(basePath + fileName))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdDocument);
            encodedText = new String(text.getBytes("Cp1252"), "Cp1251");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedText;
    }

    public static void writeToTxt(String fileName){
        String text = readPdf(fileName);
        String file = basePath + fileName.substring(0, fileName.length() - 4) + ".txt";

        try {
            Files.write(Paths.get(file), Collections.singleton(text));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
