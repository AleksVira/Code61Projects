package com.umbrellait.lab1_java;

public class Factorial {

    /**
     * Напишите функцию, которая вычисляет факториал от 10
     */

    public static int calculate() {
        int result = 1;
        for (int i = 2; i <= 10; i++) {
            result *= i;
        }
        return result;
    }

}
