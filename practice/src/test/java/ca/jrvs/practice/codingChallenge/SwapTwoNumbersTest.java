package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwapTwoNumbersTest {
    SwapTwoNumbers swapTwoNumbers = new SwapTwoNumbers();

    @Test
    public void SwapTwoNumber() {
        int[] testArray = new int[]{2, 3};
        swapTwoNumbers.usingBitManipulation(testArray, 0, 1);
        assertArrayEquals(new int[]{3, 2}, testArray);

        int[] testArray2 = new int[]{2, 3};
        swapTwoNumbers.usingArithmetic(testArray2, 0, 1);
        assertArrayEquals(new int[]{3, 2}, testArray2);

        int[] testArray3 = new int[]{10, 20, 30, 40, 50};
        swapTwoNumbers.usingBitManipulation(testArray3, 1, 4);
        assertArrayEquals(new int[]{10, 50, 30, 40, 20}, testArray3);

        int[] testArray4 = new int[]{10, 20, 30, 40, 50};
        swapTwoNumbers.usingArithmetic(testArray4, 1, 4);
        assertArrayEquals(new int[]{10, 50, 30, 40, 20}, testArray4);
    }
}