package org.example;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        Map<Integer,String> hashMap = new HashMap<>();
        hashMap.put(15, "Apple");
        hashMap.put(10, "Banana");
        hashMap.put(20, "Orange");
        //hashMap.put(15, "apple"); // ko cho phép key trùng lặp
        hashMap.put(30, "Apple"); // Nhiều key có thể trỏ đến 1 value
        System.out.println(hashMap);

        // truy cập giá trị từ key
        if (hashMap.containsKey(30)){
            System.out.println(hashMap.get(30));
        }

        // duyệt các khóa trong map bằng keyset
        for (Integer key : hashMap.keySet()) {
            System.out.println("Khóa: " + key + ", Giá trị: " + hashMap.get(key));
        }

        // duyệt các giá trị
        for(String value : hashMap.values()){
            System.out.println(value);
        }


    }
}