package com.example.securitydemo.Service;

import com.ibm.icu.text.CharsetDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class DetectorUtil {
    public static String getCharsetName(byte[] file) throws IOException {
        log.info("START getCharsetName");
        CharsetDetector detector = new CharsetDetector();
        detector.setText(file);
        String detectedCharset = detector.detect().getName();
        log.info("END getCharsetName: [CHARSET:{}]", detectedCharset);
        return detectedCharset;
    }
}
