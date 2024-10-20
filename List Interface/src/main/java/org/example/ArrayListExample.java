package org.example;

import java.util.ArrayList;

public class ArrayListExample {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        // Thêm phần tử vào ArrayList
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");

        // Truy cập phần tử theo chỉ số
        System.out.println("Phần tử tại chỉ số 1: " + arrayList.get(1));  // Banana

        // Thay đổi phần tử tại chỉ số 2
        arrayList.set(2, "Date");
        System.out.println("ArrayList sau khi thay đổi: " + arrayList);

        // Xóa phần tử
        arrayList.remove("Apple");
        System.out.println("ArrayList sau khi xóa: " + arrayList);
    }
}
