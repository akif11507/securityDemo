package com.example.securitydemo.model;

public class MyThreadWithInterface implements Runnable{
    @Override
    public void run() {
        System.out.println("this is thread from interface "+Thread.currentThread());
        System.out.println("helloooo");
    }
}
