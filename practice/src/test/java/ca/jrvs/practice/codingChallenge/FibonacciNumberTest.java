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
    public void fibDynamin() {
        assertEquals(1, fibonacciNumber.fibDynamin(1));
        assertEquals(55, fibonacciNumber.fibDynamin(9));
        assertEquals(89, fibonacciNumber.fibDynamin(10));
    }
}