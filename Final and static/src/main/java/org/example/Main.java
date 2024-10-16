package org.example;

public class Main {
    public static void main(String[] args) {
        // Sử dụng thuộc tính company chung cho tất cả các đối tượng
        Staff newStaffA = new Staff("Nguyễn Văn A", 30);
        Staff newStaffB = new Staff("Nguyễn Văn B", 25);
        newStaffA.display();
        newStaffB.display();

        // truy cập đến thuộc tính, phương thức static
        Staff.staticMethod();
        System.out.println(Staff.company);

        // biến final
        final double PI = 3.146;

        // Biến final ko thể chỉnh sửa
        //Pi = 3.1256; // báo lỗi
        
    }
}