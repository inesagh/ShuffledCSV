package com;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Controller.control();
//        Controller.controlForPdf("1.pdf");
        List<String> filesName = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            filesName.add(i + ".pdf");
        }
        Controller.controlForReadingNormalPdf(filesName);
    }
}
