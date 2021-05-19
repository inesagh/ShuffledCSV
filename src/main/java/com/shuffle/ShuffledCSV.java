package com.shuffle;

import com.creatingcsv.CSVFiles;
import com.creatingcsv.Person;
import com.github.javafaker.Faker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ShuffledCSV {
    CSVFiles csvFiles = new CSVFiles();

    public File createShuffledCSV() {
        File file = new File("shuffledcsv.csv");
        csvFiles.createTwoCSV();
        File csv1File = new File("C:\\Users\\user\\Documents\\csv1.csv");
        File csv2File = new File("C:\\Users\\user\\Desktop\\csv2.csv");
        ArrayList<Person> csv1 = csvFiles.readCSVs(csv1File);
        ArrayList<Person> csv2 = csvFiles.readCSVs(csv2File);

        try (BufferedWriter bufferedWriter3 = new BufferedWriter(new FileWriter(file));
             CSVPrinter csvPrinter3 = new CSVPrinter(bufferedWriter3, CSVFormat.DEFAULT
                     .withHeader("ID", "First Name", "Last Name", "Date", "Gender", "Race", "Phone Number", "Email"))) {

            class Write {
                public synchronized void write(int i, List<Person> list) {
                    Person person = list.get(i);

                        try {
                            csvPrinter3.printRecord(person.getId(), person.getFirstName(), person.getLastName(), person.getDate(), person.getGender(), person.getRace(), person.getPhoneNumber(), person.getEmail());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
            Write write = new Write();
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < csv1.size(); i++) {
                    write.write(i, csv1);
                    write.write(i, csv2);
                }
                try {
                    csvPrinter3.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
                try {
                    completableFuture.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully created the shuffled csv.");

        return file;
    }

    public File updateEverySecond(File file) {
            ArrayList<Person> people = csvFiles.readCSVs(file);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                         .withHeader("ID", "First Name", "Last Name", "Date", "Gender", "Race", "Phone Number", "Email"))) {

                for (int i = 1; i < people.size(); i++) {
                    Person person = people.get(i - 1);
                    if (i % 2 == 0) {
                        Faker faker = new Faker();
                        person.setFirstName(faker.name().firstName());
                    }
                    csvPrinter.printRecord(person.getId(), person.getFirstName(), person.getLastName(), person.getDate(), person.getGender(), person.getRace(), person.getPhoneNumber(), person.getEmail());
                }
                csvPrinter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("Successfully updated every second row.");

        return file;
    }

    public File deleteEveryFifth(File file) {
            ArrayList<Person> people = csvFiles.readCSVs(file);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                         .withHeader("ID", "First Name", "Last Name", "Date", "Gender", "Race", "Phone Number", "Email"))) {

                for (int i = 1; i <= people.size(); i++) {
                    Person person = people.get(i - 1);
                    if (i % 5 != 0) {
                        csvPrinter.printRecord(person.getId(), person.getFirstName(), person.getLastName(), person.getDate(), person.getGender(), person.getRace(), person.getPhoneNumber(), person.getEmail());
                    }
                }
                csvPrinter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("Successfully deleted every fifth row.");
        return file;
    }
}
