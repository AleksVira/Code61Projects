package com.umbrellait.lab1_java;

import java.util.ArrayList;
import java.util.List;

public class OddEven {

    /**
     * Реализовать функцию, которая проверяет - четное число или нет
     */

    public static boolean isEven(int number) {
        return (number % 2 == 0);
    }

    /**
     * Реализовать функцию, которая фильтрует список и возвращает список только четных чисел
     */

    public static List<Integer> filterEven(List<Integer> numbers) {
        List<Integer> filterResult = new ArrayList<>();
        for (Integer integer : numbers) {
            if (isEven(integer)) {
                filterResult.add(integer);
            }
        }
        return filterResult;
    }

    /**
     * Реализовать функцию, которая фильтрует список и возвращает список только нечетных чисел
     */

    public static List<Integer> filterOdd(List<Integer> numbers) {
        List<Integer> filterResult = new ArrayList<>();
        for (Integer integer : numbers) {
            if (!isEven(integer)) {
                filterResult.add(integer);
            }
        }
        return filterResult;
    }

}
