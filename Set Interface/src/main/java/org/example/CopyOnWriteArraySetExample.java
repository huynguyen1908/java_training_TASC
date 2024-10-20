package org.example;

import java.io.Writer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetExample {
    public static Set<String> fruit = new CopyOnWriteArraySet<>();

    public static void main(String[] args) throws InterruptedException{
        fruit.add("Apple");
        fruit.add("Banana");

        Thread writer1 = new Thread(new WriterTask("Orange"));
        Thread writer2 = new Thread(new WriterTask("Grapes"));

        Thread reader1 = new Thread(new ReaderTask());
        Thread reader2 = new Thread(new ReaderTask());

        writer1.start();
        writer2.start();
        reader1.start();
        reader2.start();


        writer2.join();
        reader1.join();
        reader2.join();
    }

    static class WriterTask implements Runnable {
        private String e;
        public WriterTask(String e){
            this.e = e;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " đang thêm phần tử: " + e);
            fruit.add(e);
            System.out.println(Thread.currentThread().getName() + " đã thêm phần tử: " + e);
        }
    }

    static class ReaderTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " đang đọc các phần tử:");
            for (String x : fruit) {
                System.out.println(Thread.currentThread().getName() + " đọc phần tử: " + x);
            }
        }
    }

}
