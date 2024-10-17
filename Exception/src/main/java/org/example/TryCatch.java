package org.example;

import java.io.FileReader;
import java.io.IOException;

public class TryCatch {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            reader = new FileReader("test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên thủ công
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
