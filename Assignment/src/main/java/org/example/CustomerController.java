package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class CustomerController {
    private static final String FILE_NAME = "customer.txt";
    private static final Map<String, Customer> customerMap = new HashMap<>();
    private final ExecutorService readFileExecutor = Executors.newFixedThreadPool(10);
    private final ExecutorService writeFileExecutor = Executors.newFixedThreadPool(5);
    private static final long TIMEOUT = 1;

    public CustomerController(){
        loadCustomerList();
    }

    // load danh sách từ file vào
    private void loadCustomerList(){
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File đã được tạo.");
            } catch (IOException e) {
                System.out.println("Lỗi khi tạo file " + e.getMessage());
            }
        } else {
            long start = System.currentTimeMillis();
            Future<?> loadTask = readFileExecutor.submit(()-> {
                try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Customer customer = Customer.toCustomer(line);
                        customerMap.put(customer.getPhoneNumber(), customer);
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi khi đọc file: " + e.getMessage());
                }
            });


            try {
                loadTask.get(TIMEOUT, TimeUnit.SECONDS);
            }  catch (TimeoutException e) {
                System.out.println("vượt thời gian cho phép");
                loadTask.cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(loadTask.isDone()) {
                long totalTime = System.currentTimeMillis() - start;
                System.out.println("Total time to read file: " + totalTime);
            }
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
    public static void saveCustomerToFile(){
        ExecutorService writeFileExecutor = Executors.newFixedThreadPool(5);
        Future<?> writeTask = writeFileExecutor.submit(()->{
            try {
                FileWriter fw = new FileWriter(FILE_NAME,false);
                BufferedWriter bw = new BufferedWriter(fw);
                for (Customer customer : customerMap.values()) {
                    bw.write(customer.toString());
                    bw.newLine();
                }
                bw.close();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            writeTask.get(TIMEOUT,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println("Quá thời gian cho phép");
            writeTask.cancel(true);
        }

        writeFileExecutor.shutdown();
    }

    public void shutdownExecutor(){
        try {
            readFileExecutor.shutdown();
            if(!readFileExecutor.awaitTermination(TIMEOUT,TimeUnit.SECONDS)){
                readFileExecutor.shutdownNow();
            }

            writeFileExecutor.shutdown();
            if(!writeFileExecutor.awaitTermination(TIMEOUT,TimeUnit.SECONDS)){
                writeFileExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // thêm khách hàng mới
    public void add(Customer customer) {
        customerMap.put(customer.getPhoneNumber(), customer);
        saveCustomerToFile();
        System.out.println("Đã thêm khách hàng mới!");
    }


    // tìm kiếm theo phonenumber
    public Customer search(String searchedPhoneNumber){
        if (!customerMap.containsKey(searchedPhoneNumber)){
            System.out.println("Số điện thoại không tồn tại");
        }
        return customerMap.get(searchedPhoneNumber);
    }

    public void edit(String phoneNumber, Customer updateCustomer){
        Customer customer = search(phoneNumber);
        if (customerMap.containsKey(phoneNumber)){
            if (updateCustomer.getName() != null){
                customer.setName(updateCustomer.getName());
            }
            if (isValidEmail(updateCustomer.getEmail())){
                customer.setEmail(updateCustomer.getEmail());
            }
            if (isValidPhoneNumber(updateCustomer.getPhoneNumber())){
                customer.setPhoneNumber(updateCustomer.getPhoneNumber());
            }

            saveCustomerToFile();
            System.out.println("Cập nhật thành công");
        } else {
            System.out.println("Không tìm thấy thông tin khách hàng");
        }
    }


    /** Xóa khách hàng **/
    public void deleteCustomer(String phoneNumber){
        if(customerMap.containsKey(phoneNumber)){
            customerMap.remove(phoneNumber);
            System.out.println("Xóa khách hàng thành công");
            saveCustomerToFile();
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
        return Pattern.matches(phoneNumberRegex,phoneNumber) || !customerMap.containsKey(phoneNumber);
    }

    private boolean isValidEmail(String emailAddress){
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(emailRegex,emailAddress);
    }

    public void editScanner(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhập số điện thoại khách hàng cần sửa:");
        String editedCustomerPhoneNumber = scanner.nextLine();
        while (!customerMap.containsKey(editedCustomerPhoneNumber)){
            System.out.println("Số điện thoại không tồn tại");
            editedCustomerPhoneNumber = scanner.nextLine();
        }

        Customer existingCustomer = customerMap.get(editedCustomerPhoneNumber);
        System.out.println(existingCustomer);

        System.out.println("Nhập họ tên cần sửa, nếu không cần thì enter để bỏ qua");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            name = existingCustomer.getName();
        }


        System.out.println("Nhập email cần sửa, nếu không cần thì enter để bỏ qua:");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) {
            email = existingCustomer.getEmail();
        } else {
            while (!isValidEmail(email)) {
                System.out.println("Giá trị không hợp lệ, hãy nhập lại:");
                email = scanner.nextLine();
            }
        }

        System.out.println("Nhập số điện thoại, nếu không cần thì enter để bỏ qua:");
        String phoneNumber = scanner.nextLine();
        if (phoneNumber.trim().isEmpty()) {
            phoneNumber = existingCustomer.getPhoneNumber();
        } else {
            while (phoneNumber == null || phoneNumber.trim().isEmpty() || !isValidPhoneNumber(phoneNumber)) {
                System.out.println("Giá trị không hợp lệ, hãy nhập lại:");
                phoneNumber = scanner.nextLine();
            }
        }

        Customer customer = new Customer(name,email,phoneNumber);
        edit(editedCustomerPhoneNumber,customer);
    }


    public void addScanner(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhập họ tên");
        String name = scanner.nextLine();
        while (name == null || name.trim().isEmpty()) {
            System.out.println("Không để trống giá trị này, hãy nhập lại:");
            name = scanner.nextLine();
        }

        System.out.println("Nhập email:");
        String email = scanner.nextLine();
        while (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            System.out.println("Email không hợp lệ, hãy nhập lại:");
            email = scanner.nextLine();
        }

        System.out.println("Nhập số điện thoại:");
        String phoneNumber = scanner.nextLine();
        while (phoneNumber == null || phoneNumber.trim().isEmpty() || !isValidPhoneNumber(phoneNumber)) {
            System.out.println("Số điện thoại không hợp lệ, hãy nhập lại:");
            phoneNumber = scanner.nextLine();
        }

        Customer customer = new Customer(name,email,phoneNumber);
        customerMap.put(customer.getPhoneNumber(),customer);
    }

}
