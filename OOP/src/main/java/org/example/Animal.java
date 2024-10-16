package org.example;

interface Animal {
    void sound(); // phương thức trừu tượng
    default void sleep() {
        System.out.println("This animal is sleeping");
    }

}
