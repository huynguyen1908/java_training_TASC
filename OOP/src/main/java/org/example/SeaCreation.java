package org.example;

public abstract class SeaCreation {
    public abstract void moving(); // abstract method ko cần phaanf thân
    public void eat(){
        System.out.println("eat");
    }

    final void habitat() {
        System.out.println("sea");
    }
}
