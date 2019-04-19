package com.umbrellait.lab1_java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FuzzBuzzUnitTest {

    String expectedResult = "1 2 Fizz 4 Buzz 6 7 8 Fizz Buzz 11 Fizz 13 14 FizzBuzz 16 17 Fizz 19 Buzz";

    @Before
    public void init() {
    }

    @Test
    public void test_FuzzBuzz() {
        assertEquals(expectedResult, FuzzBuzz.calculate());
    }

}
