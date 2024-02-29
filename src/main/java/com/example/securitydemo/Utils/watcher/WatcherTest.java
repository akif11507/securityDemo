package com.example.securitydemo.Utils.watcher;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalTime;
import java.util.Set;

@Slf4j
public class WatcherTest {

    public static boolean status;

    public static void main(String[] args) throws IOException, InterruptedException {
        LocalTime startTime = LocalTime.now();
//        LocalTime endTime = startTime.plusMinutes(1);
        LocalTime endTime = startTime.plusSeconds(30);

        WatcherTest watcherTest = new WatcherTest();
        Thread thread1 = new Thread(() -> {
            try {
                log.info("command sent to start");
                WatcherTest.status = true;
                watcherTest.fun("/usr/local/antmedia/webapps/WebRTCAppEE/streams/stream_rtmp_5593ca3b-c9ed-461d-a0d1-e2e7b1996098/");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.isAfter(endTime)) {
                log.info("command sent to stop");
                WatcherTest.status = false;
            }
        });

        thread1.setName("threadStart");
        thread2.setName("threadStop");

        thread1.start();
        thread2.start();
////
//        if (!thread1.isAlive()) {
//            System.out.println("thread is dead");
//        }
//
//        thread1.join();
//        watcherTest.fun("/usr/local/antmedia/webapps/WebRTCAppEE/streams/stream_rtmp_5593ca3b-c9ed-461d-a0d1-e2e7b1996098/");


        watcherTest.checkStatus();
        while (true) {
//            System.out.println("keep project running");
        }
    }


    public void fun(String filePath) throws IOException, InterruptedException {
        LocalTime startTime = LocalTime.now();
//        LocalTime endTime = startTime.plusMinutes(1);
        LocalTime endTime = startTime.plusSeconds(30);
//        LocalDateTime endTime = startTime+LocalDateTime.;

        try (WatchService watchService
                     = FileSystems.getDefault().newWatchService()) {

//        Path path = Paths.get(System.getProperty(filePath));
            Path path = Paths.get((filePath));

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE
            );

            WatchKey key;
            boolean poll = true;
            while (poll) {
                key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Thread " + Thread.currentThread().getName() + " " +
                            " Event kind:" + event.kind()
                            + ". File affected: " + event.context() + ".");
                    log.info("state " + WatcherTest.status);

                }
                key.reset();
                LocalTime currentTime = LocalTime.now();
                if (!WatcherTest.status) {
                    System.out.println("this is reached");
                    poll = false;
                    watchService.close();
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error happened " + e.getMessage());
        }
    }

    public void checkStatus() {

        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        System.out.printf("%-15s \t %-15s \t %-15s \t %s\n", "Name", "State", "Priority", "isDaemon");
        for (Thread t : threads) {
            System.out.printf("%-15s \t %-15s \t %-15d \t %s\n", t.getName(), t.getState(), t.getPriority(), t.isDaemon());
        }
    }
}
