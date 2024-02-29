package com.example.securitydemo.Service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileCompareService {

    public boolean compareFiles(String file1Path, String file2Path) throws IOException, FileNotFoundException {
        InputStream input1 = new FileInputStream(file1Path);
        InputStream input2 = new FileInputStream(file2Path);

        byte[] buffer1 = new byte[16384];
        byte[] buffer2 = new byte[16384];
        int read1, read2;

        while ((read1 = input1.read(buffer1)) != -1) {
            read2 = input2.read(buffer2);
            if (read2 != read1 || !java.util.Arrays.equals(buffer1, buffer2)) {
                return false;
            }
        }

        return input2.read() == -1;
    }
}