package com.example.securitydemo.Utils.FileHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {
    public String readFileAsString(String fileName)
            throws Exception {
        String data;
        data = new String(
                Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void textFileRead(String fileName) {
        // Reading text from a file
//        FileReader;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            cntCRLF(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readAllCharsOneByOne(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        int value;
        while ((value = reader.read()) != -1) {
            System.out.println("this is per char " + value);
            content.append((char) value);

        }

        return content.toString();
    }

    public void textFileWrite() {
        // Writing text to a file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("filename.txt"))) {
            bw.write("Hello2, world!\n lskjf;alsjf \n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void binaryFileWrite() {
// Writing binary data to a file
        try (FileOutputStream fos = new FileOutputStream("filename.dat")) {
            byte[] data = {0x01, 0x02, 0x03};
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cntCRLF(BufferedReader reader) throws IOException {
        String lineFeedCode = "not_CR+LF";
        int countOfLineFeed = 0;
        int count = 0;
        int value;
        while ((value = reader.read()) != -1) {
            System.out.print(value + " ");
            if (value == 10 || value == 13) {
                count++;
                if (count == 2) {
                    lineFeedCode = "CR+LF";
                }
                if (value == 10) {
                    break;
                }
            }
        }
        System.out.println();
        System.out.println("this is final count " + count);
    }

    public void binaryFileRead() {
        // Reading binary data from a file
        try (FileInputStream fis = new FileInputStream("filename.dat")) {
            byte[] data = new byte[fis.available()];
            fis.read(data);
            System.out.println("this is binary " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String m() {
        String ans="didnt work";
        try {
            int divideByZero = 5 / 0;
            ans="worked";
            System.out.println("Rest of code in try block");
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException => " + e.getMessage());
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
//        FileHandler fileHandler = new FileHandler();




//        String path = "/home/bjit/cr_lf_test.csv";
//        String path = "/home/bjit/Downloads/Moco1-Akif.csv";
//        fileHandler.textFileRead(path);
//        fileHandler.textFileRead(path);
//        System.out.println("this is whole file as string " + output);
//        System.out.println("this is output " + fileHandler.m());
        LargeFileHandler2 fileHandler = new LargeFileHandler2();
        Thread.sleep(10000);
        fileHandler.givenUsingGuava_whenIteratingAFile_thenCorrect();
//        fileHandler.givenUsingCommonsIo_whenIteratingAFileInMemory_thenCorrect(); //out
//        fileHandler.whenStreamingThroughAFile_thenCorrect();
//        fileHandler.givenUsingApacheIo_whenStreamingThroughAFile_thenCorrect();
//        fileHandler.givenUsingBufferedReader_whenIteratingAFile_thenCorrect();
//        fileHandler.testUsingReadAllBytes();
//        fileHandler.givenUsingNewBufferedReader_whenIteratingAFile_thenCorrect();// out
//        fileHandler.givenUsingSeekableByteChannel_whenIteratingAFile_thenCorrect();
//        fileHandler.givenUsingStreamApi_whenIteratingAFile_thenCorrect();//out
        System.out.println("keep project running");
        while(true){

        }
    }
}
