package org.example;

import java.util.Vector;

public class VectorExample {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();

        // Thêm phần tử vào Vector
        vector.add("Dog");
        vector.add("Cat");
        vector.add("Horse");

        // Kiểm tra kích thước của Vector
        System.out.println("Kích thước của Vector: " + vector.size());

        // Xóa phần tử
        vector.remove("Cat");
        System.out.println("Vector sau khi xóa: " + vector);

        // Truy cập phần tử theo chỉ số
        System.out.println("Phần tử tại chỉ số 1: " + vector.get(1));
    }
}
