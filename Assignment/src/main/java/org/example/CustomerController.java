package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomerController {
    private static final String FILE_NAME = "test.txt";
    private static Map<String, Customer> customerMap = new HashMap<>();

    public CustomerController(){
        loadCustomerList();
    }

    // load danh sách từ file vào
    private void loadCustomerList(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line = br.readLine();
            while (line != null){
                Customer customer = Customer.toCustomer(line);
                customerMap.put(customer.getPhoneNumber(),customer);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Hiển thị danh sách
    public void displayCustomerList(){
        if(customerMap.isEmpty()){
            System.out.println("Chưa có khách hàng nào");
        } else {
            for(Customer customer : customerMap.values()){
                System.out.println(customer);
            }
        }
    }

    // thêm vào file
    private void saveCustomerToFile(Map<String,Customer> customerMap){
        try {
            FileWriter fw = new FileWriter("test.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Customer customer : customerMap.values()) {
                bw.write(customer.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // thêm khách hàng mới
    public void add(Customer customer) {
        customerMap.put(customer.getPhoneNumber(), customer);
        saveCustomerToFile(customerMap);
        System.out.println("Đã thêm khách hàng mới!");
//        if (isValidInfo(customer)) {
//            customerMap.put(customer.getPhoneNumber(), customer);
//            saveCustomerToFile();
//            System.out.println("Đã thêm khách hàng mới!");
//        }
    }


    // tìm kiếm theo phonenumber
    public Customer search(String searchedPhoneNumber){
        if (!customerMap.containsKey(searchedPhoneNumber)){
            System.out.println("Không tìm thấy số điện thoại");
        }
        return customerMap.get(searchedPhoneNumber);
    }

//    public void search(String searchedPhoneNumber){
//        if(customerMap.containsKey(searchedPhoneNumber)) {
//            System.out.println(customerMap.values());
//        } else {
//            System.out.println("Không tồn tại khách hàng !");
//        }
//    }

    public void edit(String phoneNumber, Customer updateCustomer){
        Customer customer = search(phoneNumber);
        if (customerMap.containsKey(phoneNumber)){
            if (updateCustomer.getName() != null){
                customer.setName(updateCustomer.getName());
            }
            if (isValidEmail(updateCustomer.getEmail())){
                customer.setName(updateCustomer.getEmail());
            }
            if (isValidPhoneNumber(updateCustomer.getPhoneNumber())){
                customer.setPhoneNumber(updateCustomer.getPhoneNumber());
            }

            saveCustomerToFile(customerMap);
            System.out.println("Cập nhật thành công");
        } else {
            System.out.println("Không tìm thấy thông tin khách hàng");
        }
    }


    // xóa khách hàng
    public void deleteCustomer(String phoneNumber){
        if(customerMap.containsKey(phoneNumber)){
            customerMap.remove(phoneNumber);
            System.out.println("Xóa khách hàng thành công");
            saveCustomerToFile(customerMap);
        } else {
            System.out.println("Không tìm thấy thông tin!");
        }
    }

    // check điều kiện
    private boolean isValidInfo(Customer customer){
        if (customer.getName() == null || customer.getPhoneNumber() == null || customer.getEmail() == null){
            System.out.println("Không để trống giá trị");
            return false;
        } else if(!isValidPhoneNumber(customer.getPhoneNumber())) {
            System.out.println("Số điện thoại không hợp lệ");
            return false;
        } else if(!isValidEmail(customer.getEmail())){
            System.out.println("Email không hợp lệ");
            return false;
        } else if(customerMap.containsKey(customer.getPhoneNumber())){
            System.out.println("Số điện thoại đã tồn tại");
            return false;
        }
        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber){
        String phoneNumberRegex = "\\d{10}";
        return Pattern.matches(phoneNumberRegex,phoneNumber);
    }

    private boolean isValidEmail(String emailAddress){
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(emailRegex,emailAddress);
    }



}
