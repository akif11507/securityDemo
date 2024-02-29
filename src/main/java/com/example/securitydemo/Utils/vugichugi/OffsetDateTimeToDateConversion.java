package com.example.securitydemo.Utils.vugichugi;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OffsetDateTimeToDateConversion {

    public static void main(String[] args) {
        // Example OffsetDateTime strings in different formats
        String[] offsetDateTimeStrings = {
                "2024-01-05T15:45+05:30",
                "2024-01-05T15:45:30+05:30",
                "2024-01-05T15:45:30.123+05:30",
                "2024-01-05T15:45:30.123456+05:30",
                "2024-01-05T15:45:30.123456789+05:30"
        };

        // Convert each string to Date
        for (String offsetDateTimeString : offsetDateTimeStrings) {
            Date date = convertToDate(offsetDateTimeString);
            System.out.println("Input String: " + offsetDateTimeString);
            System.out.println("Converted Date: " + date);
            System.out.println();
        }
    }

    private static Date convertToDate(String offsetDateTimeString) {
        // Define an array of possible date time patterns
        String[] patterns = {
                "uuuu-MM-dd'T'HH:mmXXXXX",
                "uuuu-MM-dd'T'HH:mm:ssXXXXX",
                "uuuu-MM-dd'T'HH:mm:ss.SSSXXXXX",
                "uuuu-MM-dd'T'HH:mm:ss.SSSSSSXXXXX",
                "uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSSXXXXX"
        };
        int cnt = 0;
        // Try to parse the input string using each pattern
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(offsetDateTimeString, formatter);

                // Convert OffsetDateTime to Date
                return Date.from(offsetDateTime.toInstant());
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("error at " + cnt++);
                // If parsing fails with the current pattern, try the next one
            }
        }

        // If none of the patterns match, throw an exception or handle accordingly
        throw new IllegalArgumentException("Unable to parse the input string");
    }
}

