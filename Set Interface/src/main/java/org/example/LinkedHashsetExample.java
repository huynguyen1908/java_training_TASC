package org.example;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashsetExample {
    public static void main(String[] args) {
        Set<String> linkedHashSet = new LinkedHashSet<>();

        // Thêm phần tử vào LinkedHashSet
        linkedHashSet.add("Apple");
        linkedHashSet.add("Banana");
        linkedHashSet.add("Orange");
        linkedHashSet.add("Apple");  // Phần tử trùng lặp sẽ không được thêm

        // Hiển thị LinkedHashSet (duy trì thứ tự chèn)
        System.out.println("LinkedHashSet: " + linkedHashSet);

        // Xóa phần tử
        linkedHashSet.remove("Banana");
        System.out.println("LinkedHashSet sau khi xóa Banana: " + linkedHashSet);
    }
}
