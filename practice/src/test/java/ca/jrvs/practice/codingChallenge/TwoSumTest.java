package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TwoSumTest {
    TwoSum twoSum = new TwoSum();

    int[] array1 = {50, 3, 71, 21, 69};
    int target1 = 71;
    int failedTarget = 1000;
    int[] array2 = {1, 2, 3};
    int target2 = 5;
    int[] array3 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
    int target3 = 7; 

    @Test
    public void twoSumBruteForce() {
        assertArrayEquals(new int[]{0, 3}, twoSum.twoSumBruteForce(array1, target1));
        assertArrayEquals(new int[]{-1, -1}, twoSum.twoSumBruteForce(array1, failedTarget));
        assertArrayEquals(new int[]{1, 2}, twoSum.twoSumBruteForce(array2, target2));
        assertArrayEquals(new int[]{3, 8}, twoSum.twoSumBruteForce(array3, target3));
    }

    @Test
    public void twoSumSorted() {
        assertArrayEquals(new int[]{1, 2}, twoSum.twoSumSorted(array1, target1));
        assertArrayEquals(new int[]{-1, -1}, twoSum.twoSumSorted(array1, failedTarget));
        assertArrayEquals(new int[]{1, 2}, twoSum.twoSumSorted(array2, target2));
        assertArrayEquals(new int[]{0, 5}, twoSum.twoSumSorted(array3, target3));
    }

    @Test
    public void twoSumUsingMap() {
        assertArrayEquals(new int[]{0, 3}, twoSum.twoSumUsingMap(array1, target1));
        assertArrayEquals(new int[]{-1, -1}, twoSum.twoSumUsingMap(array1, failedTarget));
        assertArrayEquals(new int[]{1, 2}, twoSum.twoSumUsingMap(array2, target2));
        assertArrayEquals(new int[]{5, 6}, twoSum.twoSumUsingMap(array3, target3));
    }
}