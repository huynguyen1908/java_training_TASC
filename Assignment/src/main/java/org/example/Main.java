package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CustomerController customerController = new CustomerController();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do{
            System.out.println("===== QUẢN LÝ KHÁCH HÀNG =====");
            System.out.println("1. Xem danh sách khách hàng");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Tìm kiếm khách hàng");
            System.out.println("4. Chỉnh sửa thông tin khách hàng");
            System.out.println("5. Xóa khách hàng");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    customerController.displayCustomerList();
                    break;
                case 2:
                    System.out.println("Nhập họ tên");
                    String name = scanner.nextLine();
                    System.out.println("Nhập email");
                    String email = scanner.nextLine();
                    System.out.println("Nhập số điện thoại");
                    String phoneNumber = scanner.nextLine();
                    customerController.add(new Customer(name,email,phoneNumber));
                    break;
                case 3:
                    System.out.println("Nhập số điện thoại cần tìm");
                    String searchedphoneNumber = scanner.nextLine();
                    customerController.search(searchedphoneNumber);
                    break;
                case 4:
                    System.out.println("Nhập số điện thoại khách hàng cần sửa:");
                    String editedCustomerPhoneNumber = scanner.nextLine();
                    System.out.println("Nhập thông tin cần chỉnh sửa:");
                    System.out.println("Họ tên:");
                    String newName = scanner.nextLine();
                    System.out.println("Email");
                    String newEmail = scanner.nextLine();
                    System.out.println("Số điện thoại");
                    String newPhoneNumber = scanner.nextLine();
                    customerController.edit(editedCustomerPhoneNumber,new Customer(newName,newEmail,newPhoneNumber));
                    break;
                case 5:
                    System.out.println("Nhập số điện thoại khách hàng cần xóa:");
                    String deletedCustomerPhoneNumber = scanner.nextLine();
                    customerController.deleteCustomer(deletedCustomerPhoneNumber);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Không hợp lệ");
            }
        } while (choice != 6);

    }
}