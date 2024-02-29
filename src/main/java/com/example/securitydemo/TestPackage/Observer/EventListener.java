package com.example.securitydemo.TestPackage.Observer;

import java.io.File;

public interface EventListener {
    void update(String eventType, File file);
}
