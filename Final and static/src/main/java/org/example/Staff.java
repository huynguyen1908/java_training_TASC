package org.example;

public class Staff {
    static String company = "TASC";
    public String name;
    public int age;

    public Staff(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void display(){
        System.out.println(name + ' ' + age + ' ' + company);
    }

    static void staticMethod(){
        System.out.println("This is static method");
    }
}
