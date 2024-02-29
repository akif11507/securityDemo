package com.example.securitydemo.Utils.vugichugi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomStringParser2 {
    public static void main(String[] args) {
        String text = "streams/stream_rtmp_ae6281f8-fff0-4c60-832e-c1f153411d90/hls/stream_rtmp_ae6281f8-fff0-4c60-832e-c1f153411d90-2023-12-22_06-00-41.965000005719_1703237155596_2.12s.ts";
        String expected = "streams/stream_rtmp_ae6281f8-fff0-4c60-832e-c1f153411d90/hls/1703237155596_2.12s.ts";
        System.out.println(extractFileName(text) + " " + extractFileName(text).equals(expected));
        System.out.println(getSuffixFromFileName(text) + " " + getSuffixFromFileName(text).equals(expected));
    }

    private static String extractFileName(String text) {
        StringBuilder firstSegment = new StringBuilder();
        StringBuilder secondSegment = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < text.length(); i++) {
            temp.append(text.charAt(i));
            if (text.charAt(i) == '.') found = true;
            if (text.charAt(i) == '/' && !found) {
                firstSegment.append(temp);
                temp.delete(0, temp.length());
            } else if (found) {
                secondSegment.append(text.charAt(i));
            }
        }

        temp.delete(0, temp.length());
        StringBuilder secondSegmentTemp = new StringBuilder();
        for (int i = secondSegment.length() - 1; i >= 0; i--) {
            if (secondSegment.charAt(i) == '_') {
                secondSegmentTemp.append(temp);
                temp.delete(0, temp.length());
            }
            temp.append(secondSegment.charAt(i));
        }

        secondSegmentTemp.reverse();
        return String.valueOf(firstSegment) + secondSegmentTemp;
    }

    private static String getSuffixFromFileName(String mediaFileName) {
        String pattern = "-(\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}\\.\\d{3})";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(mediaFileName);
        String suffix = "";
        if (matcher.find()) {
            suffix = matcher.group(1);
        }
        return suffix;
    }
}
