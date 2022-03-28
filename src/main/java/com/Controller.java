package com;

import com.creatingpdf.Service;
import com.policequestions.PdfToJSONService;
import com.shuffle.ShuffledCSV;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Controller {
    public static void control(){
        ShuffledCSV shuffledCSV = new ShuffledCSV();
        CompletableFuture<File> createShuffledCSV = CompletableFuture.supplyAsync(() -> shuffledCSV.createShuffledCSV());

        try {
            createShuffledCSV.thenCompose(s -> CompletableFuture.supplyAsync(() -> shuffledCSV.updateEverySecond(s)))
                    .thenCompose(s -> CompletableFuture.supplyAsync(() -> shuffledCSV.deleteEveryFifth(s)))
                    .get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void controlForPdf(String fileName){
        Service.writeToTxt(fileName);
    }

    public static String controlForReadingNormalPdf(List<String> filesName){
        return PdfToJSONService.pdfsToJSON(filesName);
    }
}
