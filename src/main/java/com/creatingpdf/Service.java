package com.creatingpdf;


import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class Service {
    private static String basePath = "C:\\Users\\user\\Desktop\\";
    private static String basePathForDownloads = "/home/orion/Downloads/";

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

    public static void writeToTxt(String fileName) {
        String text = readPdf(fileName);
        String file = basePath + fileName.substring(0, fileName.length() - 4) + ".txt";

        try {
            Files.write(Paths.get(file), Collections.singleton(text));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readNormalPdfAndSaveImages(String fileName, int index) {
        String content = "";

        try (PDDocument pdDocument = PDDocument.load(new File(basePathForDownloads + fileName))) {
            PDFTextStripper stripper = new PDFTextStripper();
            content = stripper.getText(pdDocument);
//            saveImages(pdDocument, index);
//            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static void saveImages(PDDocument pdDocument, int index) throws IOException {
        for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
            PDResources pdResources = pdDocument.getPage(i).getResources();
            for (COSName csName : pdResources.getXObjectNames()) {
                PDXObject pdxObject = pdResources.getXObject(csName);
                if (pdxObject instanceof PDImageXObject) {
                    PDStream pdStream = pdxObject.getStream();
                    PDImageXObject image = new PDImageXObject(pdStream, pdResources);
                    // image storage location and image name
                    File imgFile = new File(basePathForDownloads + index + "_" + i + ".png");
                    ImageIO.write(image.getImage(), "png", imgFile);
                }
            }
        }
    }
}

//            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
//            BufferedImage image = pdfRenderer.renderImage(0);
//            ImageIO.write(image, "PNG", new File(basePath + "myImage.jpg"));
