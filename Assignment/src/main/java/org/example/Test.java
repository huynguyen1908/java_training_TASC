package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        String st = "Nguyen văn a";
        String b = "Nhuea asfgaa ";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt",true));
            bw.write(st);
            bw.newLine();
            bw.write(b);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
