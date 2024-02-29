package com.example.securitydemo.Utils.vugichugi.EnumTest;

public class Constants {
    public static final String COMMON_API_PATH = "/avs/api";
    public enum Operation {
        ADD("add"),
        SUBTRACT("subtract"),
        DIVIDE("divide"),
        MULTIPLY("multiply");
        public final String label;
        Operation(String label) {
            this.label = label;
        }
    }

}
