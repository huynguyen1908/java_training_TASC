package org.example;

public class Main {
    public static void main(String[] args) {
        // s1 được thêm vào string pool
        String s1 = "hello world";

        // s2 được lưu trong vùng nhớ heap
        String s2 = new String("Hello world");

        String s3 = "hello world";

        // phân biệt hoa thường
        boolean check = s1.equals(s2);
        System.out.println(check);

        // Không phân biệt hoa hay thường
        check = s1.equalsIgnoreCase(s2);
        System.out.println(check);

        // địa chỉ của s1 s2 khác nhau nên trả về false
        if (s1 == s2) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        /* 
        do trong string pool đã có chuỗi hello world nên s3 sẽ tham chiếu đến chuỗi đó trong string pool => s1 s3 cùng địa chỉ vùng nhớ
        toán tử so sánh "==": so sánh địa chỉ vùng nhớ của 2 biến
         */
        if (s1 == s3) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}