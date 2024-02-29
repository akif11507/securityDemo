package com.example.securitydemo.Utils.Threads;

class Hi extends Thread{
    public void run() {
        for (int i=0;i<5;i++){
            System.out.println("HI");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class Hello extends Thread {
    public void run() {
        for (int i=0;i<5;i++){
            System.out.println("Hello2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
public class MultiThreading {
    public static void main(String[] args) throws InterruptedException {
        Hi obj1 = new Hi();
        Hello obj2 = new Hello();
        obj1.start();
        obj2.start();
    }
}
