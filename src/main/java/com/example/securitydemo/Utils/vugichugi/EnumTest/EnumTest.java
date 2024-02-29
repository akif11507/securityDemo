package com.example.securitydemo.Utils.vugichugi.EnumTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class EnumTest {

    @Value("${spring.application.name}")
    private String name;

    public static void main(String[] args) {
//        ZoneId zoneId = ZoneId.of("Europe/Tallinn");
        Date date = new Date();
        System.out.println(date);
        long EpochMilliSecondsAtDate = date.toInstant().toEpochMilli()+100L;

        System.out.println(EpochMilliSecondsAtDate);
//        EnumTest enumTest = new EnumTest();
//        enumTest.check();
//        System.out.println(DateTimeFormat.ISO.DATE.);
//        System.out.println(Constants.COMMON_API_PATH + " " + enumTest.name);
//        System.out.println(Constants.Operation.ADD.name() + " " + Constants.Operation.ADD.name().toLowerCase() + " " + Constants.Operation.ADD.label);
    }
    public void check(){

        System.out.println();
        EnumTest enumTest = new EnumTest();
        System.out.println(Constants.COMMON_API_PATH + " " + name);
    }
}
