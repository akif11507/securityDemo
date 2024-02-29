package com.example.securitydemo.Utils.MediaParse;

import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.MediaSegment;
import io.lindstrom.m3u8.parser.MediaPlaylistParser;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
public class MediaParse {

    public static void main(String[] args) {
        MediaParse mediaParse = new MediaParse();
        mediaParse.parseMediaPlaylist();
    }

    private void parseMediaPlaylist() {

        MediaPlaylist playlist;
        MediaSegment mediaSegment = null;
        playlist = parseM3u8File();
        int size = playlist.mediaSegments().size();
        if (size != 0) {
            mediaSegment = playlist.mediaSegments().get(size - 1);
        }

        //Bug work item ID 98417 if there is no seconds in datetime then we will pick datetime from previous segments
        if (mediaSegment != null && mediaSegment.programDateTime().isPresent()) {
            int len = mediaSegment.uri().length();
            log.info(mediaSegment.uri());
            String fileName = mediaSegment.uri().substring(0, mediaSegment.uri().length() - 3);
            long currentEpochTime = System.currentTimeMillis();
            double fileDuration = mediaSegment.duration();
            log.info(fileName + "_" + currentEpochTime + "_" + fileDuration + "_s.ts");



            String programDateTimeString = mediaSegment.programDateTime().get().toString();
            if (programDateTimeString.length() <= 17) {
                MediaSegment previousSegment = playlist.mediaSegments().get(size - 2);
                Optional<OffsetDateTime> programDateTime = previousSegment.programDateTime();

                OffsetDateTime offsetDateTime = programDateTime.get();
                Optional<OffsetDateTime> previousProgramDateTime = Optional.of(offsetDateTime.plusSeconds((long) mediaSegment.duration()));
                mediaSegment = MediaSegment.builder().programDateTime(previousProgramDateTime)
                        .duration(mediaSegment.duration()).uri(mediaSegment.uri()).build();
            }
        }

    }

    public MediaPlaylist parseM3u8File() {
        MediaPlaylistParser parser = new MediaPlaylistParser();
        MediaPlaylist playlist = null;
        String WEBRTCAPPEE_STREAM_PATH = "/home/bjit/Downloads/streams_stream_rtmp_328a9756-2a45-4963-b785-da2d10cd46b8_hls_stream_rtmp_328a9756-2a45-4963-b785-da2d10cd46b8-2023-11-20_05-08-07.678.m3u8";
        try {
            playlist = parser.readPlaylist(Paths.get(WEBRTCAPPEE_STREAM_PATH));
        } catch (Exception e) {
            log.info("Error while parsing Media segment" + e.getMessage());
            log.info("Error class name " + e.getClass().getCanonicalName());
        } finally {
            parser = null;
        }

        return playlist;
    }
}
