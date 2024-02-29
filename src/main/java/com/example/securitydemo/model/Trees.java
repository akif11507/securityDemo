package com.example.securitydemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
public enum Trees {

    OAK(1,"CSV"),
    CHESTNUT(2,".TXT"),
    UNKNOWN(3,null);

    @Getter
     final Integer value;
    @Getter
    private final String extension;

    public static Trees get(Integer value) {
        Trees[] values = Trees.values();
        return Stream.of(values)
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
