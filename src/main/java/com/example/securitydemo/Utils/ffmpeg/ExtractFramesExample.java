package com.example.securitydemo.Utils.ffmpeg;


import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffmpeg.*;
import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ExtractFramesExample {
    public static void main(String[] args) throws IOException {
//        if (args.length != 1) {
//            System.err.println("Exactly 1 argument expected: path to media file");
//            System.exit(1);
//        }
        String dirPath = "/home/bjit/Videos/ffmpeg/";
        String pathToVideo = dirPath + "sample_640x360.ts";

        String input = dirPath + "sample_640x360.ts";
        String output = dirPath;

//        usingFFprobe(pathToVideo);

//        usingFFmpeg(pathToVideo);

//        extract(input,output);

        convert();

    }

    private static void usingFFmpeg(String pathToVideo) {
        final AtomicLong durationMillis = new AtomicLong();

//        FFmpegResult ffmpegResult = FFmpeg.atPath()
//                .addInput(
//                        UrlInput.fromUrl(pathToVideo)
//                )
//                .addOutput(new NullOutput())
//                .setProgressListener(new ProgressListener() {
//                    @Override
//                    public void onProgress(FFmpegProgress progress) {
//                        durationMillis.set(progress.getTimeMillis());
//                    }
//                })
//                .execute();
//
//        System.out.println("Exact duration: " + durationMillis.get() + " milliseconds");
    }

    private static void usingFFprobe(String pathToVideo) {
        FFprobeResult result = FFprobe.atPath()
                .setShowStreams(true)
                .setInput(pathToVideo)
                .execute();

        for (Stream stream : result.getStreams()) {
            System.out.println("Stream #" + stream.getIndex()
                    + " type: " + stream.getCodecType()
                    + " duration: " + stream.getDuration() + " seconds");
        }
    }

    private static void extract(String src, String dest) {
        Path pathToSrc = Paths.get(src);
        final Path pathToDstDir = Paths.get(dest);
        FFmpeg.atPath()
                .addInput(UrlInput
                        .fromPath(pathToSrc)
                )
                .addOutput(FrameOutput
                                .withConsumer(
                                        new FrameConsumer() {
                                            private long num = 1;

//                                    @Override
//                                    public void consumeStreams(List<Stream> streams) {
//                                        // All stream type except video are disabled. just ignore
//                                    }

                                            @Override
                                            public void consumeStreams(List<com.github.kokorin.jaffree.ffmpeg.Stream> list) {

                                            }

                                            @Override
                                            public void consume(Frame frame) {
                                                // End of Stream
                                                if (frame == null) {
                                                    return;
                                                }

                                                try {
                                                    String filename = "frame_" + num++ + ".png";
                                                    Path output = pathToDstDir.resolve(filename);
                                                    ImageIO.write(frame.getImage(), "png", output.toFile());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                )
                                // No more then 100 frames
                                .setFrameCount(StreamType.VIDEO, 100L)
                                // 1 frame every 10 seconds
//                        .setFrameRate(0.1)
                                // Disable all streams except video
                                .disableStream(StreamType.AUDIO)
                                .disableStream(StreamType.SUBTITLE)
                                .disableStream(StreamType.DATA)
                )
                .execute();
    }

    private static void convert() throws IOException {
        String protocol = "-protocol_whitelist";
        String terms = "concat,file,http,https,tcp,tls,crypto";
        Path dir = Path.of("/home/bjit/Videos/ffmpeg");
        Path indir = dir.resolve("out/1707973442466.m3u8");
        Path outdir = dir.resolve("live_stream_simulation.mp4");
//        Path pathToSrc = Paths.get(src);
//        final Path pathToDstDir = Paths.get(dest);
        try {
            FFmpeg.atPath()

                    .addInput(UrlInput.fromPath(indir).addArguments(protocol, terms))

                    .addArguments("-t", "60")
                    .addArguments("-bsf:a", "aac_adtstoasc")
                    .addArguments("-c", "copy")
                    .addOutput(
                            UrlOutput.toPath(outdir)
                    )
                    .setOverwriteOutput(true)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
