package org.example;

public class Main {
    public static void main(String[] args) {
        /* primitive and object data types
         */
        int x = 5;
        int y = x;
        System.out.println(x);
        System.out.println(y);

        // Tính tham chiếu
        Student a = new Student("Nguyễn Văn A");
        Student b = a;
        b.setName("Nguyễn Văn B");
        System.out.println(a.getName());
        System.out.println(b.getName());

        /* So sánh
        Kiểu dữ liệu object được unboxing để so sánh với primitive
         */
        Integer temp = 10;
        if (temp == x) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}