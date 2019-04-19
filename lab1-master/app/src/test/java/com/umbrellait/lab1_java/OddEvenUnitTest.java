package com.umbrellait.lab1_java;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OddEvenUnitTest {

    private List<Integer> numbers = new ArrayList<>();

    @Before
    public void init() {
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(42);
        numbers.add(67);
        numbers.add(97);
        numbers.add(23);
        numbers.add(12);
        numbers.add(62);
        numbers.add(81);
        numbers.add(86);
        numbers.add(100);
    }

    @Test
    public void test_IsEven() {
        assertTrue("number is odd, expected even", OddEven.isEven(4));
        System.out.println("test_IsEven passed");
    }

    @Test
    public void test_IsOdd() {
        assertFalse("number is even, expected odd", OddEven.isEven(7));
        System.out.println("test_IsOdd passed");
    }

    @Test
    public void test_EvenArray() {
        List<Integer> evenNumbers = new ArrayList<>();
        evenNumbers.add(2);
        evenNumbers.add(4);
        evenNumbers.add(42);
        evenNumbers.add(12);
        evenNumbers.add(62);
        evenNumbers.add(86);
        evenNumbers.add(100);
        assertEquals("list contains odd values", evenNumbers, OddEven.filterEven(numbers));
        System.out.println("test_EvenArray passed");
    }

    @Test
    public void test_OddArray() {
        List<Integer> oddNumbers = new ArrayList<>();
        oddNumbers.add(1);
        oddNumbers.add(3);
        oddNumbers.add(67);
        oddNumbers.add(97);
        oddNumbers.add(23);
        oddNumbers.add(81);
        assertEquals("list contains odd values", oddNumbers, OddEven.filterOdd(numbers));
        System.out.println("test_OddArray passed");
    }

}