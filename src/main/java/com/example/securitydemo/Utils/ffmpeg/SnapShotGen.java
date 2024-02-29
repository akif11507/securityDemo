package com.example.securitydemo.Utils.ffmpeg;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class SnapShotGen {
    public static void main(String[] args) {
        SnapShotGen gen = new SnapShotGen();
        gen.fun();
    }

    public void fun() {
        String dirPath = "/home/bjit/Videos/ffmpeg/";
        String input = dirPath + "sample_640x360.ts";
        String output = dirPath + "demo.jpg";

        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", input, "-frames:v",
                "1", output, "-y");
        try {
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            log.error("Image generation failed {} ", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            log.error("Image generation failed {} ", e.getMessage());
        }
    }
}
