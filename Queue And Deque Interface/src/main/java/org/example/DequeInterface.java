package org.example;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Deque;

public class DequeInterface {
    public static void main(String[] args) {
        // ArrayDeque triển khai Deque
        Deque<Integer> num1 = new ArrayDeque<>();
        // thêm vào deque
        num1.offer(1);
        num1.offerLast(2);
        num1.offerFirst(3);
        System.out.println(num1);

        // truy cập phần tử đầu, cuối
        System.out.println("First Element: " + num1.peekFirst());
        System.out.println("Last Element: " + num1.peekLast());


        // LinkedList triển khai Deque
        Deque<Integer> num2 = new LinkedList<>();

        // thêm phần tử vào đầu cuối
        num2.addFirst(10);
        num2.addLast(20);
        num2.addFirst(5);

        // Truy cập phần tử ở đầu,cuối
        System.out.println("First Element: " + num2.peekFirst());
        System.out.println("last Element: " + num2.peekLast());

        // Loại bỏ phần tử theo nguyên tắc LIFO (pop)
        num2.pop();
        System.out.println("Deque sau khi pop: " + num2);
    }
}