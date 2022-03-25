package com;

import com.creatingpdf.Service;
import com.shuffle.ShuffledCSV;

import java.io.File;
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

    public static String controlForReadingNormalPdf(String fileName){
        return Service.readNormalPdf(fileName);
    }
}
