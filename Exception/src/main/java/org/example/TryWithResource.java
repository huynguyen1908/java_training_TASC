package org.example;

import java.io.BufferedReader;
import java.io.FileReader;

public class TryWithResource {
    public static void main(String[] args){
        String filePath = new String("test.txt");
        try (FileReader reader = new FileReader(filePath)){
            while (reader != null) {
                System.out.println(reader);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
