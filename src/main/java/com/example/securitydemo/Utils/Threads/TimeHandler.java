package com.example.securitydemo.Utils.Threads;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TimeHandler {
    private void timeInIso(String startDate) {
        String format = String.valueOf(DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("this is iso " + Timestamp.from(Instant.now()));

    }

    private void convertIso(String startDate) {
        String pattern = "yyyy-MM-dd'T'HH:mmX";
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern(pattern);
        DateTimeFormatter isoFormat = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(startDate, customFormat);
        LocalDateTime currentTime = LocalDateTime.now();

        System.out.println("this is convert dateTime " + dateTime);
//        System.out.println("this is convert currentTime " + currentTime.format(DateTimeFormatter.ofPattern(pattern)));
//        System.out.println(currentTime.isAfter(dateTime));
//        2023-06-16T16:50:15.123
//        2023-06-16T16:36:07.977565
    }

    public static Date ParseISO8601Datetime(String dateTimeString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(dateTimeString);
    }

    private void oneTimeKey() {
        String oneTimeKey = RandomStringUtils.randomAlphanumeric(100);
        System.out.println("this is one time key " + oneTimeKey);
    }


    @Value("${spring.application.name}")
    public static String connectionStringTest;

    public static void main(String[] args) throws ParseException {
//        TimeHandler timeHandler = new TimeHandler();
//        DBConfiguration configuration = new DBConfiguration();
//        System.out.println("this is dat : " + configuration.getUsername());
//        String time = "2023-06-16T16:50+06";
//        System.out.println(ParseISO8601Datetime("2023-08-08T12:28:17.705Z"));
//        timeHandler.timeInIso("");
//        timeHandler.convertIso(time);
//        timeHandler.oneTimeKey();
        Thread t1 = new Thread(() -> {
            while (true) {
//                System.out.println("first thread is running");
                if (Thread.currentThread().isInterrupted()) return;
            }

        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            while (true) {
//                System.out.println("second thread is running");
            }
        }, "Thread-2");

        t1.start();
//        t2.start();

        for (long i = 0; i < 10000000000L; i++) {
            if (i == 9000000000L) {
                if (t1.isAlive()) {
                    t1.interrupt();
                    System.out.println("Closing first thread");
//                    break;
//                    Thread.
//                    if(t1.isInterrupted())Thread.currentThread().interrupt();
                }
            }
//            if (i == 20000L) {
//                if (t2.isAlive()) {
//                    System.out.println("Closing second thread");
//                    t2.interrupt();
//                    if(t2.isInterrupted())Thread.currentThread().interrupt();
//                }
//            }
        }

    }
}
