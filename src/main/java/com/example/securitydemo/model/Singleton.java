package com.example.securitydemo.model;

import java.util.Objects;

public final class Singleton {
    private static Singleton instance;
    public String name;
    public String property;

    private Singleton(String name, String property) {
        this.name = name;
        this.property = property;
    }

    public static Singleton getInstance(String name, String property) {
        if (Objects.isNull(instance)) {
            instance = new Singleton(name, property);
        }
        return instance;
    }
}
