package org.example;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetExample {
    public static void main(String[] args) {
        Set<String> treeSet = new TreeSet<>();

        // Thêm phần tử vào TreeSet
        treeSet.add("Apple");
        treeSet.add("Banana");
        treeSet.add("Orange");
        treeSet.add("Kumquat");
        treeSet.add("Apple");  // Phần tử trùng lặp sẽ không được thêm

        // Hiển thị TreeSet (sắp xếp theo thứ tự bảng chữ cái)
        System.out.println("TreeSet: " + treeSet);

        // Lấy phần tử đầu tiên (nhỏ nhất) và cuối cùng (lớn nhất)
        System.out.println("Min: " + ((TreeSet<String>) treeSet).first());
        System.out.println("Max: " + ((TreeSet<String>) treeSet).last());

        Set<Integer> num = new TreeSet<>();
        num.add(20);
        num.add(15);
        num.add(71);
        num.add(63);
        // được sắp xếp tăng dần
        System.out.println("Number list: " + num);

        // ptu đầu tiên
        System.out.println(((TreeSet<Integer>) num).first());
    }
}
