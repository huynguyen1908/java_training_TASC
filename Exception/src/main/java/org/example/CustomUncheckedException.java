package org.example;

public class CustomUncheckedException extends RuntimeException{
    public CustomUncheckedException(String message) {
        super(message);
    }
}
