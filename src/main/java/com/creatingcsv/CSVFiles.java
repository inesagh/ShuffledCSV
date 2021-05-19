package com.creatingcsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CSVFiles {

    public String createTwoCSV() {
        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter("C:\\Users\\user\\Documents\\csv1.csv"));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter("C:\\Users\\user\\Desktop\\csv2.csv"))) {
            CSVPrinter csvPrinter1 = new CSVPrinter(writer1, CSVFormat.DEFAULT
                    .withHeader("ID", "First Name", "Last Name", "Date", "Gender", "Race", "Phone Number", "Email"));
            CSVPrinter csvPrinter2 = new CSVPrinter(writer2, CSVFormat.DEFAULT
                    .withHeader("ID", "First Name", "Last Name", "Date", "Gender", "Race", "Phone Number", "Email"));


            class MyThread implements Runnable {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        Person person1 = Service.createRandomData();
                        Person person2 = Service.createRandomData();

                        try {
                            csvPrinter1.printRecord(person1.getId(), person1.getFirstName(), person1.getLastName(), person1.getDate(),
                                    person1.getGender(), person1.getRace(), person1.getPhoneNumber(), person1.getEmail());
                            csvPrinter2.printRecord(person2.getId(), person2.getFirstName(), person2.getLastName(), person2.getDate(),
                                    person2.getGender(), person2.getRace(), person2.getPhoneNumber(), person2.getEmail());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new MyThread());
            completableFuture.get();

            csvPrinter1.flush();
            csvPrinter2.flush();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return "Successfully created two csv files.";
    }

    public ArrayList<Person> readCSVs(File file) {
        ArrayList<Person> arrayList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                String[] splitedString = row.split(",");
                if (!splitedString[0].equals("ID")) {
                    Person person = new Person(splitedString[0], splitedString[1], splitedString[2], splitedString[3], Gender.valueOf(splitedString[4]), Race.valueOf(splitedString[5]), splitedString[6], splitedString[7]);
                    arrayList.add(person);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;

    }

}
