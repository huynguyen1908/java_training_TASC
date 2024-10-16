package org.example;

public class Dog implements Animal{

    // phương thức trừu tượng sound
    @Override
    public void sound() {
        System.out.println("Gâu Gâu");
    }
}
