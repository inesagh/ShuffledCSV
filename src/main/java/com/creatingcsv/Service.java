package com.creatingcsv;

import com.github.javafaker.Faker;
import com.github.javafaker.IdNumber;
import java.util.Calendar;
import java.util.Date;

public class Service {

    public static Person createRandomData() {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        Date date = faker.date().birthday();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String birthdate = calendar.get(Calendar.YEAR) + "/" + Calendar.MONTH + "/" + Calendar.DAY_OF_MONTH;
        Gender gender = Gender.values()[(int) Math.floor(Math.random() * 2)];
        Race race = Race.values()[(int) Math.floor(Math.random() * 5)];
        String phoneNumber = faker.phoneNumber().cellPhone();
        String emailAddress = faker.internet().emailAddress();
        IdNumber idNumber = faker.idNumber();
        String id = idNumber.valid();

        return new Person(id, firstName, lastName, birthdate, gender, race, phoneNumber, emailAddress);
    }

}
