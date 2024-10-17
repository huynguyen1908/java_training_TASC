package org.example;

public class CustomException{
    public static void checkScore(int score) {
        if (score < 0) {
            // Ném ngoại lệ tùy chỉnh nếu điều kiện không thỏa mãn
            throw new CustomUncheckedException("Score cannot be negative!");
        } else {
            System.out.println("Score is valid.");
        }
    }

    public static void checkAge(int age) throws CustomCheckedException {
        if (age < 18) {
            // Ném ngoại lệ tùy chỉnh nếu điều kiện không thỏa mãn
            throw new CustomCheckedException("Age is less than 18!");
        } else {
            System.out.println("Access granted.");
        }
    }

    public static void main(String[] args) {
        // Gọi phương thức có thể ném ngoại lệ tùy chỉnh
        checkScore(-1);

        try {
            // Gọi phương thức có thể ném ngoại lệ tùy chỉnh
            checkAge(15);
        } catch (CustomCheckedException e) {
            e.printStackTrace();
        }
    }

}