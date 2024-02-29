package com.example.securitydemo.Utils.vugichugi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomStringParser {
    public static void main(String[] args) {
        String text = "stream_rtmp_5498dd54-e3bf-43c4-904e-7f466b561ba9-2023-12-20_02-49-12.509000000061_1703040674717_2.002s.ts";
//        System.out.println(extractFileNo(text));
        System.out.println(extractFileNumber(text));
    }

    public static String extractFileNo(String text) {
        StringBuilder extractedSegment = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < text.length(); i++) {
            if (found) {
                if (text.charAt(i) == '_') break;
                extractedSegment.append(text.charAt(i));
            }
            if (text.charAt(i) == '.') {
                found = true;
            }
        }

        int cnt = 0;
        StringBuilder ans = new StringBuilder();
        for (int i = extractedSegment.length() - 1; i >= 0 && cnt < 9; i--, cnt++) {
            ans.append(extractedSegment.charAt(i));
        }
        ans.reverse();
        System.out.println("extracted " + extractedSegment + " output " + ans);
        return ans.toString();
    }
    public static String extractFileNumber(String text) {
        Pattern pattern = Pattern.compile("(\\d{12})");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String fileNumber = matcher.group(1);
            return fileNumber.substring(Math.max(fileNumber.length() - 9, 0));
        }
        return "";
    }
}
