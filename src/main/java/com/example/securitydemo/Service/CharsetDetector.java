package com.example.securitydemo.Service;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CharsetDetector {
    public Charset detectCharset(File f, String[] charsets) {

        Charset charset = null;

        for (String charsetName : charsets) {
            charset = detectCharset(f, Charset.forName(charsetName));
            if (charset != null) {
                break;
            }
        }

        return charset;
    }

    private Charset detectCharset(File f, Charset charset) {
        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));

            CharsetDecoder decoder = charset.newDecoder();
            decoder.reset();

            byte[] buffer = new byte[512];
            boolean identified = false;
            while ((input.read(buffer) != -1) && (!identified)) {
                identified = identify(buffer, decoder);
            }

            input.close();

            if (identified) {
                System.out.println(charset);
                return charset;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    private boolean identify(byte[] bytes, CharsetDecoder decoder) {
        try {
            decoder.decode(ByteBuffer.wrap(bytes));
        } catch (CharacterCodingException e) {
            return false;
        }
        return true;
    }

    private static void check(String path) throws IOException {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String s;

        try {
            // new input stream reader is created
            fis = new FileInputStream(path);
            isr = new InputStreamReader(fis);

            // the name of the character encoding returned
            s = isr.getEncoding();
            System.out.print("Character Encoding: " + s);

        } catch (Exception e) {
            // print error
            System.out.print("The stream is already closed");
        } finally {
            // closes the stream and releases resources associated
            if (fis != null)
                fis.close();
            if (isr != null)
                isr.close();
        }


    }
    public static boolean checkUTF8(byte[] barr){

        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        ByteBuffer buf = ByteBuffer.wrap(barr);

        try {
            decoder.decode(buf);

        }
        catch(CharacterCodingException e){
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        String path = "/home/bjit/Downloads/valid1_invalid_CONDEV 1491-2.csv";
        File f = new File(path);

        String[] charsetsToBeTested = {"UTF-8", "windows-1253", "ISO-8859-7"};

        CharsetDetector cd = new CharsetDetector();
        Charset charset = cd.detectCharset(f, charsetsToBeTested);

        if (charset != null) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(f), charset);
                int c = 0;
                while ((c = reader.read()) != -1) {
                    System.out.print((char) c);
                }
                reader.close();
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        } else {
            System.out.println("Unrecognized charset.");
        }

        DetectorUtil.getCharsetName(Files.readAllBytes(f.toPath()));
        System.out.println(checkUTF8(Files.readAllBytes(f.toPath())));
        check(path);
//        Charset
    }


}
