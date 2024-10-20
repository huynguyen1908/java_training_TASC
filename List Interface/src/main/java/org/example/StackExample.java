package org.example;

import java.util.Stack;

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        // Thêm phần tử vào Stack (push)
        stack.push(5);
        stack.push(10);
        stack.push(15);

        // Truy cập phần tử đầu tiên mà không xóa (peek)
        System.out.println("Phần tử trên cùng của Stack: " + stack.peek());  // 15

        // Lấy và xóa phần tử trên cùng (pop)
        System.out.println("Phần tử được lấy ra: " + stack.pop());  // 15
        System.out.println("Stack sau khi pop: " + stack);
    }
}
