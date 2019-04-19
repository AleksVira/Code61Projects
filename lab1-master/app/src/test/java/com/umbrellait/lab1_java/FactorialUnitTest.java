package com.umbrellait.lab1_java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FactorialUnitTest {

    private int expectedResult = 3_628_800;

    @Before
    public void init() {
    }

    @Test
    public void test_Factorial() {
        assertEquals(expectedResult, Factorial.calculate());
    }

}
