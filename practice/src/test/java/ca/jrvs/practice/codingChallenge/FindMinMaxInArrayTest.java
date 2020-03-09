package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FindMinMaxInArrayTest {
    FindMinMaxInArray findMinMaxInArray = new FindMinMaxInArray();

    @Test
    public void FindMinMaxInArray() {
        int[] testArray1 = new int[] {1, 2, 3, 4, 5};
        int[] testArray2 = new int[] {100, 50, 400};
        int[] testArray3 = new int[] {10, 9, 8, 7};

        assertArrayEquals(new int[]{1, 5}, findMinMaxInArray.usingForLoop(testArray1));
        assertArrayEquals(new int[]{1, 5}, findMinMaxInArray.usingStreams(testArray1));
        assertArrayEquals(new int[]{1, 5}, findMinMaxInArray.usingJavaApi(testArray1));

        assertArrayEquals(new int[]{50, 400}, findMinMaxInArray.usingForLoop(testArray2));
        assertArrayEquals(new int[]{50, 400}, findMinMaxInArray.usingStreams(testArray2));
        assertArrayEquals(new int[]{50, 400}, findMinMaxInArray.usingJavaApi(testArray2));

        assertArrayEquals(new int[]{7, 10}, findMinMaxInArray.usingForLoop(testArray3));
        assertArrayEquals(new int[]{7, 10}, findMinMaxInArray.usingStreams(testArray3));
        assertArrayEquals(new int[]{7, 10}, findMinMaxInArray.usingJavaApi(testArray3));
    }
}