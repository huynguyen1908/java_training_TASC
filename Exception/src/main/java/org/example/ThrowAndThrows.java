package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ThrowAndThrows {
    public static void throwExample() {
        throw new ArrayIndexOutOfBoundsException("Array out of bound");
    }

    // throws được sử dụng cho checked exception
    public static void main(String args[]) throws FileNotFoundException {
        FileReader reader = new FileReader("test.txt");
        try {
            throwExample();
        }catch (ArrayIndexOutOfBoundsException  e){
            System.out.println("Something went wrong");
        }
    }
}
