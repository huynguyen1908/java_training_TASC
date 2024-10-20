package org.example;

import java.util.HashSet;
import java.util.Set;

public class Hashset {
    public static void main(String[] args) {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("chuối");
        hashSet.add("táo");
        hashSet.add("lê");
        hashSet.add("táo"); // phần tử trùng lặp ko được thêm
        System.out.println("HashSet: " + hashSet);

        // kiểm tra phần tử có tồn tại ko
        if(hashSet.contains("chuối")) {
            System.out.println("true");
        }

        hashSet.remove("chuối");
        System.out.println("HashSet sau khi xóa: " + hashSet);

        Set<Student> studentList = new HashSet<>();
        studentList.add(new Student("Nguyễn Văn A", 15));
        studentList.add(new Student("Nguyễn Văn B", 17));
        studentList.add(new Student("Trần Thị K", 16));

        // in danh sách
        for(Student x : studentList){
            System.out.println(x.toString());
        }

        // tìm kiếm 1 object
        Student searched = new Student("Nguyễn Văn A");
        if(studentList.contains(searched)) {
            System.out.println("true");
        }


    }
}