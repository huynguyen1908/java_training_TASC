package org.example;

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();
        dog.sound();
        cat.sound();

        Fish fish = new Fish();
        fish.moving(); // triển khai abstract method
        fish.eat(); // method thông thường
        fish.habitat(); // final method có thể dc kế thừa
    }
}