package com.example.securitydemo.Utils.FileHandler;

import com.google.common.io.Files;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.stream.Stream;

public class LargeFileHandler2 {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    long mx = 0;

    // tests - iterate lines in a file
    // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";
    String path = "/home/bjit/Downloads/testfiledownload.com.dat";

    //    String path2 = "G:\\full\\train\\input\\" + "trainDataPositive.csv";
//    String fileName = "src/test/resources/myFile";
    String fileName = "/home/bjit/Downloads/testfiledownload.com.dat";


    public String targetDirectoryPath = "/home/bjit/Downloads/redirect/";

    public void fileTransfer(String sourceFilePath) {
        try {
            // Create Path objects for source file and target directory
            Path sourcePath = Paths.get(sourceFilePath);
            Path targetDirectory = Paths.get(targetDirectoryPath);

            // Check if the target directory exists; if not, create it
            if (!java.nio.file.Files.exists(targetDirectory)) {
                java.nio.file.Files.createDirectories(targetDirectory);
            }

            // Specify the target file path by combining the target directory and source file name
            Path targetFilePath = targetDirectory.resolve(sourcePath.getFileName());

            // Copy the source file to the target directory
            java.nio.file.Files.copy(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);


            System.out.println("Chunk uploaded successfully to: " + targetFilePath);
        } catch (FileAlreadyExistsException e) {
            System.err.println("Chunk already exists in the target directory.");
        } catch (IOException e) {
            System.err.println("An error occurred during file upload: " + e.getMessage());
        }

    }

    public final void givenUsingGuava_whenIteratingAFile_thenCorrect() throws IOException {


        logMemory();
//        Files.readLines(new File(path), Charsets.UTF_8);
//        File
//        File f = new File(path);
//
//        byte[] bytes3 = com.google.common.io.Files.toByteArray(new File(path));

        File file = new File(path);
        byte[] bytes = new byte[(int) file.length()];
        // funny, if can use Java 7, please uses Files.readAllBytes(path)
        try(FileInputStream fis = new FileInputStream(file)){
            fis.read(bytes);
            bytes = new byte[1];
        }
        logMemory();
    }


    public final void givenUsingCommonsIo_whenIteratingAFileInMemory_thenCorrect() throws IOException {
        //


        logMemory();
//        FileUtils.readLines(new File(path));
        byte[] bytes = FileUtils.readFileToByteArray(new File(path));
//        byte[] bytes2 = IOUtils.toByteArray(new FileInputStream(path));
//        ByteBuffer bb = ByteBuffer.wrap(bytes);
//        bb.clear();
        logMemory();
    }


    public final void whenStreamingThroughAFile_thenCorrect() throws IOException {
        LocalTime start = LocalTime.now();
        System.out.println("Starting at " + start);


        logMemory();

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            int cnt = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // System.out.println(line);
                System.out.println("line num " + cnt++);
                logMemory();
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

        logMemory();
        System.out.println("Highest used memory " + mx);
        LocalTime end = LocalTime.now();
        System.out.println("Ending at " + end);
        System.out.println("Total time taken " + (Duration.between(start, end).getSeconds()));
    }


    public final void givenUsingApacheIo_whenStreamingThroughAFile_thenCorrect() throws IOException {
        LocalTime start = LocalTime.now();
        System.out.println("Starting at " + start);

        logMemory();

        final LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        try {
//        try (final LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8")) {
            int cnt = 1;
            while (it.hasNext()) {
                final String line = it.nextLine();
                // do something with line
                System.out.println("line num " + cnt++);
                logMemory();
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        logMemory();
        System.out.println("Highest used memory " + mx);
        LocalTime end = LocalTime.now();
        System.out.println("Ending at " + end);
        System.out.println("Total time taken " + (Duration.between(start, end).getSeconds()));
    }

    public void testUsingReadAllBytes() throws IOException {
        LocalTime start = LocalTime.now();
        System.out.println("Starting at " + start);
        logMemory();
        java.nio.file.Files.readAllBytes(Path.of(fileName));
        logMemory();
        System.out.println("Highest used memory " + mx);
        LocalTime end = LocalTime.now();
        System.out.println("Ending at " + end);
        System.out.println("Total time taken " + (Duration.between(start, end).getSeconds()));
    }

    public void givenUsingBufferedReader_whenIteratingAFile_thenCorrect() throws IOException {
        LocalTime start = LocalTime.now();
        System.out.println("Starting at " + start);
        logMemory();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int cnt = 1;
            while (br.readLine() != null) {
                // do something with each line
                System.out.println("line num " + cnt++);
                logMemory();
//                fileTransfer(fileName);
            }
        }
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        try {
            int cnt = 1;
            while (br.readLine() != null) {
                // do something with each line
                System.out.println("line num " + cnt++);
                logMemory();
//                fileTransfer(fileName);
            }
        } finally {
            fr.close();
            br.close();
        }

        logMemory();
        System.out.println("Highest used memory " + mx);
        LocalTime end = LocalTime.now();
        System.out.println("Ending at " + end);
        System.out.println("Total time taken " + (Duration.between(start, end).getSeconds()));
    }


    public void givenUsingNewBufferedReader_whenIteratingAFile_thenCorrect() throws IOException {
        LocalTime start = LocalTime.now();
        System.out.println("Starting at " + start);


        logMemory();
        try (BufferedReader br = java.nio.file.Files.newBufferedReader(Paths.get(fileName))) {
            int cnt = 1;
            while (br.readLine() != null) {
                // do something with each line
                System.out.println("line num " + cnt++);
                logMemory();
            }
        }

        logMemory();
        System.out.println("Highest used memory " + mx);
        LocalTime end = LocalTime.now();
        System.out.println("Ending at " + end);
        System.out.println("Total time taken " + (Duration.between(start, end).getSeconds()));
    }


    public void givenUsingSeekableByteChannel_whenIteratingAFile_thenCorrect() throws IOException {


        logMemory();
        try (SeekableByteChannel ch = java.nio.file.Files.newByteChannel(Paths.get(fileName), StandardOpenOption.READ)) {
            ByteBuffer bf = ByteBuffer.allocate(1000);
            while (ch.read(bf) > 0) {
                bf.flip();
                // System.out.println(new String(bf.array()));
                bf.clear();
            }
        }
        logMemory();
    }


    public void givenUsingStreamApi_whenIteratingAFile_thenCorrect() throws IOException {


        logMemory();
        try (Stream<String> lines = java.nio.file.Files.lines(Paths.get(fileName))) {
            lines.forEach(line -> {
                // do something with each line
            });
        }
        logMemory();
    }

    // utils

    private final void logMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 1048576;
        long totalMemory = Runtime.getRuntime().totalMemory() / 1048576;
        long freeMemory = Runtime.getRuntime().freeMemory() / 1048576;
        long usedMemory = totalMemory - freeMemory;
        mx = Math.max(mx, usedMemory);
        logger.info("Max Memory: {} Mb, Total Memory: {} Mb, Free Memory: {} Mb, Used Memory: {} Mb"
                , maxMemory, totalMemory, freeMemory, usedMemory);
    }


}
