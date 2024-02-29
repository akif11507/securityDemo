package com.example.securitydemo.Utils.Threads.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Demo {

    private final Executor executor = Executors.newFixedThreadPool(40);

    public static void main(String[] args) throws InterruptedException {


        Demo demo = new Demo();
        ArrayList<Transaction> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new Transaction(Integer.toString(i), "description " + i));
        }
//        demo.usingNormal(data);
//        demo.usingParallel(data);
//        demo.usingThreads(data);
        demo.usingFuture(data);
//        while (true) {
//            Thread.sleep(5000);
//        }
    }

    public void usingNormalInitial() {
        long start = System.currentTimeMillis();
        var categories = Stream.of(
                        new Transaction("1", "description 1"),
                        new Transaction("2", "description 2"),
                        new Transaction("3", "description 3"),
                        new Transaction("4", "description 4"),
                        new Transaction("5", "description 5"),
                        new Transaction("6", "description 6"),
                        new Transaction("7", "description 7"),
                        new Transaction("8", "description 8"),
                        new Transaction("9", "description 9"),
                        new Transaction("10", "description 10")
                )
                .map(CategorizationService::categorizeTransaction)
                .collect(toList());
        long end = System.currentTimeMillis();

        System.out.printf("The operation took %s ms%n", end - start);
        System.out.println("Categories are: " + categories);
    }

    public void usingNormal(ArrayList<Transaction> data) {
        long start = System.currentTimeMillis();
        var categories = data.stream()
                .map(CategorizationService::categorizeTransaction)
                .collect(toList());
        long end = System.currentTimeMillis();

        System.out.printf("The operation took %s ms%n", end - start);
        System.out.println("Categories are: " + categories);
    }

    public void usingThreads(ArrayList<Transaction> data) {
        long start = System.currentTimeMillis();

        List<Category> categories = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (Transaction transaction : data) {
            Thread thread = new Thread(() -> {
                Category category = CategorizationService.categorizeTransaction(transaction);
                synchronized (categories) {
                    categories.add(category);
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();

        System.out.printf("The operation took %s ms%n", end - start);
        System.out.println("Categories are: " + categories);
    }

    public void usingParallel(ArrayList<Transaction> data) {
        long start = System.currentTimeMillis();
        var categories = data.stream()
                .parallel()
                .map(CategorizationService::categorizeTransaction)
                .collect(toList());
        long end = System.currentTimeMillis();
        System.out.println("No. of processors" + Runtime.getRuntime().availableProcessors());
        System.out.printf("The operation took %s ms%n", end - start);
        System.out.println("Categories are: " + categories);
    }

    public void usingFuture(ArrayList<Transaction> data) {

        long start = System.currentTimeMillis();
        var futureCategories = data.stream()
                .map(transaction -> CompletableFuture.supplyAsync(
                        () -> CategorizationService.categorizeTransaction(transaction), executor)
                )
                .collect(toList());

        var categories = futureCategories.stream()
                .map(CompletableFuture::join)
                .collect(toList());

        long end = System.currentTimeMillis();

//        executor.shutdown();
        System.out.printf("The operation took %s ms%n", end - start);
        System.out.println("Categories are: " + categories);

    }
}