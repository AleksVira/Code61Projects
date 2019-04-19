package com.umbrellait.lab1_java;

// В тесте ошибка! В строке Expected есть "6", хотя должно было быть "Fizz"

public class FuzzBuzz {
    /**
     * Напишите функцию, которая выводит числа от 1 до 20 (включительно)
     * <p>
     * Для каждого числа, которое кратно 3, выведите Fizz вместо числа
     * Для каждого числа, которое кратно 5, выведите Buzz вместо числа
     * Если число кратно и 3, и 5 - выведите FizzBuzz вместо числа
     */

    public static String calculate() {
        StringBuilder result = new StringBuilder("");
        for (int i = 1; i < 21; i++) {
            if (i % 3 == 0) {
                result.append("Fizz");
            }
            if (i % 5 == 0) {
                result.append("Buzz");
            }
            if (i % 3 != 0 && i % 5 != 0) {
                result.append(i);
            }
            result.append(" ");
        }
        return result.toString().trim();
    }
}
