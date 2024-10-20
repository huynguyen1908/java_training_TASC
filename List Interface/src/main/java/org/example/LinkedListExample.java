package org.example;

import java.util.LinkedList;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        // Thêm phần tử vào đầu và cuối danh sách
        linkedList.addFirst(10);
        linkedList.addLast(20);
        linkedList.add(30);  // Tương đương với addLast

        // Truy cập và hiển thị phần tử đầu tiên và cuối cùng
        System.out.println("Phần tử đầu tiên: " + linkedList.getFirst());  // 10
        System.out.println("Phần tử cuối cùng: " + linkedList.getLast());  // 30

        // Xóa phần tử đầu tiên
        linkedList.removeFirst();
        System.out.println("LinkedList sau khi xóa phần tử đầu: " + linkedList);
    }
}
