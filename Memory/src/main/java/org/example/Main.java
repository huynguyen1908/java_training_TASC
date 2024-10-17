package org.example;

public class Main {
    public static void main(String[] args) {
        // biến tĩnh, cục bộ được lưu treen stack
        int staticVariable = 10;

        // cấp phát động, được lưu trên heap
        String dynamicVariable = new String("this is dynamic variable");
    }
}