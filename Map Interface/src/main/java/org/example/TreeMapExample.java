package org.example;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapExample {
    public static void main(String[] args) {
        // Tạo một TreeMap
        Map<Integer, String> treeMap = new TreeMap<>();
        // Thêm các cặp key-value vào TreeMap
        treeMap.put(10, "22024579");
        treeMap.put(30, "22024514");
        treeMap.put(20, "22025782");

        // Hiển thị nội dung TreeMap (theo thứ tự sắp xếp theo khóa)
        System.out.println("TreeMap: " + treeMap);
    }
}
