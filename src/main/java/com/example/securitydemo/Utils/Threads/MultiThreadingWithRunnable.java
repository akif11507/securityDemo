package com.example.securitydemo.Utils.Threads;

import lombok.Getter;

import java.util.ArrayList;

@Getter
class Hi2 implements Runnable {
    public Integer val;

    Hi2(Integer val) {
        this.val = val;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
//            System.out.println("Hi2");
            System.out.println("Running from thread " + val);
            if (i == 4) System.out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

//class Hello2 implements Runnable {
//    public void run() {
//        for (int i=0;i<5;i++){
//            System.out.println("Hello2");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}
public class MultiThreadingWithRunnable {
    public static void main(String[] args) throws InterruptedException {
////        Runnable obj1 = ;
////        Runnable obj2 = ;
//
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 5; i++) {
//                System.out.println("Hi2");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < 5; i++) {
//                System.out.println("Hello2");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        t1.setPriority(10);
//        t2.setPriority(1);
//        t1.start();
//        t2.start();
//
//        t1.join();t2.join();
//        System.out.println("bye");
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Hi2 hi2 = new Hi2(i);
            Thread thread = new Thread(hi2);
            thread.setName("Thread-" + i);
            threads.add(thread);


//            System.out.println("this si name and id " + thread.getId() + " " + thread.getName());
        }
        for (var curThread : threads) {
            curThread.start();
        }
        Thread curThread = Thread.currentThread();
        if (curThread.getName().equals("Thread-3")) {
            System.out.println("Joining started");
            curThread.join();
        }

    }
}
