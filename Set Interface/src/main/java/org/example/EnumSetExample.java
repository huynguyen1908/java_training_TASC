package org.example;

import java.util.EnumSet;
import java.util.Set;

public class EnumSetExample {
    enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

    public static void main(String[] args) {
        // Sử dụng EnumSet
        Set<Day> enumSet = EnumSet.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY);

        // Hiển thị EnumSet
        System.out.println("EnumSet: " + enumSet);

        // Thêm phần tử
        enumSet.add(Day.SUNDAY);
        System.out.println("EnumSet sau khi thêm SUNDAY: " + enumSet);
    }
}
