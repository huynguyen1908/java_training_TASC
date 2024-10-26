package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class FileReaderTask implements Runnable{

    private static final String FILE_NAME = "customer.txt";
    private static final Map<String, Customer> customerMap = new HashMap<>();
    public void readFile(String FILE_NAME) throws InterruptedException {
        int threadNum = 2;
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        long start = System.currentTimeMillis();

        Future<?> addFile1 = executor.submit(()->{
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("File đã được tạo.");
                } catch (IOException e) {
                    System.out.println("Lỗi khi tạo file " + e.getMessage());
                    }
            } else {
                try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Customer customer = Customer.toCustomer(line);
                        customerMap.put(customer.getPhoneNumber(), customer);
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi khi đọc file: " + e.getMessage());
                }
            }
        });

        Future<?> addFile2 = executor.submit(()->{
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("File đã được tạo.");
                } catch (IOException e) {
                    System.out.println("Lỗi khi tạo file " + e.getMessage());
                }
            } else {
                try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Customer customer = Customer.toCustomer(line);
                        customerMap.put(customer.getPhoneNumber(), customer);
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi khi đọc file: " + e.getMessage());
                }
            }
        });
        while (!addFile2.isDone() || !addFile1.isDone()){
            Thread.sleep(1);
        }
        long totalTime = System.currentTimeMillis() - start;
        System.out.println(totalTime);
        executor.shutdown();
    }


    @Override
    public void run() {

    }

    private synchronized void saveCustomerToFile(){

    }
}
