package com.example.securitydemo.Utils.vugichugi;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateTimeParse {
    public static void main(String[] args) {
                DateTimeParse parse = new DateTimeParse();
        // Using OffsetDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.now();

//        System.out.println("OffsetDateTime: " + offsetDateTime);

//        String date = "2024-01-04T15:10";
//        OffsetDateTime dt = OffsetDateTime.parse(date);
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        System.out.println(fmt.format(dt));

        String[] offsetDateTimeStrings = {
//                "2018-12-05T15:45:00Z",
                "2024-01-05T15:45+05:30",
                "2024-01-05T15:45:30+05:30",
                "2024-01-05T15:45:30.123+05:30",
                "2024-01-05T15:45:30.123456+05:30",
                "2024-01-05T15:45:30.123456789+05:30"
        };
        String[] patterns = {
                "uuuu-MM-dd'T'HH:mmXXXXX",
                "uuuu-MM-dd'T'HH:mm:ssXXXXX",
                "uuuu-MM-dd'T'HH:mm:ss.SSSXXXXX",
                "uuuu-MM-dd'T'HH:mm:ss.SSSSSSXXXXX",
                "uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSSXXXXX"
        };
        for (String offsetDateTimeString : offsetDateTimeStrings) {
//            parse.parseCustom(offsetDateTimeString);
        }

        System.out.println("ans " + parse.parseISO8601Datetime("2024-01-04T15:10:05.048+06:00"));
//        System.out.println(offsetDateTime);
//        String dateString = "2024-01-04T15:01:35.384+06:00";
//        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
//        OffsetDateTime offsetDateTime = OffsetDateTime.now();
//        Instant instant = Instant.parse(dateString);
//
//        Date date = Date.from(instant);
//        System.out.println("parsed " + date.getTime());
//        String dateString = "2024-01-04T14:27:21.950+06:00";
//        String pattern1 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
//        String pattern2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
//        String pattern3 = "yyyy-MM-dd'T'HH:mm'Z'";
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern1);
////        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        try {
//            Date parsedDate = dateFormat.parse(dateString);
////            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(dateString);
//            // Convert the parsed Date to OffsetDateTime
////            OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(parsedDate.toInstant(), ZoneOffset.UTC);
//            System.out.println(parsedDate);
//        } catch (ParseException e) {
//            e.printStackTrace(); // Handle the exception appropriately
//        }
    }

    private Date parseISO8601Datetime(String dateTimeString) {
        log.info("date len " + dateTimeString + " " + dateTimeString.length());
        Date date = null;
        try {
            if (dateTimeString.length() == 29) {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH).parse(dateTimeString);
            } else if (dateTimeString.length() == 20) {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(dateTimeString);
            } else {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH).parse(dateTimeString);
            }

        } catch (ParseException e) {
            log.error("Date parse failed with date: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unable to parse date: {}", e.getMessage());
            log.error("Error class name {} ", e.getClass().getCanonicalName());
        }
        return date;
    }

    private void parseCustom(OffsetDateTime dateTimeString){
//        OffsetDateTime dt = OffsetDateTime.parse(dateTimeString);
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        System.out.println(fmt.format(dt));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSSXXXXX");
//        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, formatter);

        // Convert OffsetDateTime to Date
        System.out.println("OffsetDateTime: " + dateTimeString);
        System.out.println(Date.from(dateTimeString.toInstant()));
    }
}
//2024-01-08T03:39:20.176912Z
//2024-01-08T09:39:16.580699935+06:00
