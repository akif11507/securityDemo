//package com.example.securitydemo.model;
//
//import java.io.IOException;
//
//public class MultiThreadedServer {
//    private final MostFrequentWordService mostFrequentWordService = new MostFrequentWordService();
//
//    public MultiThreadedServer(int port) throws IOException {
//        var serverSocket = new ServerSocket(port);
//        while (true) {
//            var socket = serverSocket.accept();
//
//            var thread = new Thread(() -> handle(socket));
//            thread.start();
//        }
//    }
//
//    //rest of the code.
//}
