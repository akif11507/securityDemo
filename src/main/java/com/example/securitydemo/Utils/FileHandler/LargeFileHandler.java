package com.example.securitydemo.Utils.FileHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class LargeFileHandler {

    public String targetDirectoryPath = "path/to/target/directory/";

    public void fileTransfer(String sourceFilePath) {
        try {
            // Create Path objects for source file and target directory
            Path sourcePath = Paths.get(sourceFilePath);
            Path targetDirectory = Paths.get(targetDirectoryPath);

            // Check if the target directory exists; if not, create it
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            // Specify the target file path by combining the target directory and source file name
            Path targetFilePath = targetDirectory.resolve(sourcePath.getFileName());

            // Copy the source file to the target directory
            Files.copy(sourcePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File uploaded successfully to: " + targetFilePath);
        } catch (FileAlreadyExistsException e) {
            System.err.println("File already exists in the target directory.");
        } catch (IOException e) {
            System.err.println("An error occurred during file upload: " + e.getMessage());
        }

    }

    public void bufferTest(String filePath) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.readLine() != null) {
                // do something with each line
//                fileTransfer(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filesBufferTest(String filePath) throws FileNotFoundException {
        try (BufferedReader br = java.nio.file.Files.newBufferedReader(Paths.get(filePath))) {
            while (br.readLine() != null) {
                // do something with each line
//                fileTransfer(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void seekableTest(String filePath) throws FileNotFoundException {
        try (SeekableByteChannel ch = java.nio.file.Files.newByteChannel(Paths.get(filePath), StandardOpenOption.READ)) {
            ByteBuffer bf = ByteBuffer.allocate(1000);
            while (ch.read(bf) > 0) {
                bf.flip();
                // System.out.println(new String(bf.array()));
                bf.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void streamsTest(String filePath) throws IOException {
        try (Stream<String> lines = java.nio.file.Files.lines(Paths.get(filePath))) {
            lines.forEach(line -> {
                // do something with each line
            });
        }
    }

    public void apacheTest(String filePath) throws IOException {
        LineIterator it = FileUtils.lineIterator(new File(filePath), "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                // do something with line
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

}
