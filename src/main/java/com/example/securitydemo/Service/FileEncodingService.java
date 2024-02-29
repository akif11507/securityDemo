package com.example.securitydemo.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;

@Service
public class FileEncodingService {

    public void changeFileEncoding(String curFilePath,String newFilePath, String currentEncoding, String newEncoding) throws IOException {
        // Read the file using the current encoding
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(curFilePath), Charset.forName(currentEncoding)));

        // Create a new writer with the desired encoding
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFilePath), Charset.forName(newEncoding)));

        // Write the contents of the file to the new writer
        char[] buffer = new char[16384];
        int read;
        while ((read = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, read);
        }

        // Close the reader and writer
        reader.close();
        writer.close();
    }
}
