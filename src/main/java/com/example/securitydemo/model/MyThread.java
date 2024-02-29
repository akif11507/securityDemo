package com.example.securitydemo.model;

public class MyThread extends Thread{
    @Override
    public void run(){
        System.out.println("this is thread "+Thread.currentThread());
        System.out.println("helloooo");
    }
}
