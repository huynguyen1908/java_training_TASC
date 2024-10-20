package org.example;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapExample {
    public static void main(String[] args) {
        // Tạo một LinkedHashMap
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();

        // Thêm các cặp key-value vào LinkedHashMap
        linkedHashMap.put(10, "Apple");
        linkedHashMap.put(20, "Banana");
        linkedHashMap.put(12, "Orange");

        // Hiển thị nội dung LinkedHashMap (theo thứ tự chèn)
        System.out.println("LinkedHashMap: " + linkedHashMap);
    }
}
