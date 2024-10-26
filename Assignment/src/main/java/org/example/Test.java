package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt",true));
            bw.write(sc.nextLine());
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
