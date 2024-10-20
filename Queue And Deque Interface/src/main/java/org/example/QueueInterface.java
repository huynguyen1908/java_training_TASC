package org.example;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueInterface {
    public static void main(String[] args){
        //Linkedlist triển khai Queue
        // theo FIFO thông thường
        Queue<Integer> queue1 = new LinkedList<>();
        queue1.add(1);
        queue1.add(3);
        queue1.add(2);
        System.out.println(queue1);

        //PriorityQueue triển khai Queue
        //Không tuân theo FIFO truyền thống mà là sắp xếp các phần tử từ nhỏ đến lớn
        Queue<Integer> queue2 = new PriorityQueue<>();
        queue2.add(5);
        queue2.add(7);
        queue2.add(2);
        System.out.println(queue2);

        //ArrayDeque triển khai Queue
        Queue<Integer> queue3 = new ArrayDeque<>();
        queue3.add(5);
        queue3.add(7);
        queue3.add(2);
        System.out.println(queue3);
    }
}
