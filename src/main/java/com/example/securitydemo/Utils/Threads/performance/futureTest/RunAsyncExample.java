package com.example.securitydemo.Utils.Threads.performance.futureTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunAsyncExample {
    public static void main(String[] args) {
        Runnable runnable1 = () -> {
            System.out.println("Hello from Task 1::" + Thread.currentThread().getName());
        };
        CompletableFuture<Void> taskCompletableFuture1 = CompletableFuture.runAsync(runnable1);
        System.out.println("Hello from Main::" + Thread.currentThread().getName());
//        taskCompletableFuture1.join();

        Runnable runnable2 = () -> {
            System.out.println("Hello from Task 2::" + Thread.currentThread().getName());
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
//        CompletableFuture<Void> taskCompletableFuture2 = CompletableFuture.runAsync(runnable2, executorService);
        CompletableFuture<Void> taskCompletableFuture2 = CompletableFuture.runAsync(runnable2);
//        taskCompletableFuture2.join();
        executorService.shutdown();
    }
}
