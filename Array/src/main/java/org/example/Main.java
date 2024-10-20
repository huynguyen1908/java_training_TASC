package org.example;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

    // sử dụng mảng ko có hàm sắp xếp nên ta phải tự code
    public static void arraySort(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++){
            for (int j = i; j < n; j++){
                if (arr[i] > arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
    public static void main(String[] args) {
        // khai báo mảng
        int[] arr = new int[] {10, 11, 24, 52, 12, 5, 2, 63, 12, 16};
        // truy cập phần tử mảng
        System.out.println( arr[0]);

        arraySort(arr, 10);
        for (int x: arr) {
            System.out.print(x + " ");
        }

        // sử dụng arraylist
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(3);
        arrayList.add(15);
        //xóa vị trí tại 1
        arrayList.remove(1);
    }
}