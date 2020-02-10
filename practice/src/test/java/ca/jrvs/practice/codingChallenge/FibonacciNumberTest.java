package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciNumberTest {

    FibonacciNumber fibonacciNumber = new FibonacciNumber();

    @Test
    public void fibRecursive() {
        assertEquals(1, fibonacciNumber.fibRecursive(1));
        assertEquals(55, fibonacciNumber.fibRecursive(9));
        assertEquals(89, fibonacciNumber.fibRecursive(10));
    }

    @Test
    public void fibDynamic() {
        assertEquals(1, fibonacciNumber.fibDynamic(1));
        assertEquals(55, fibonacciNumber.fibDynamic(9));
        assertEquals(89, fibonacciNumber.fibDynamic(10));
    }
}