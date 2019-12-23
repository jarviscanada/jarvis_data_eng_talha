package ca.jrvs.apps.practice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LambdaStreamExcImpTest {

    private LambdaStreamExc lse;

    @Before
    public void setUp() throws Exception {
        lse = new LambdaStreamExcImp();
    }

    @After
    public void tearDown() throws Exception {
        lse = null;
    }

    @Test
    public void createStrStream() {
        List<String> expected = Arrays.asList("string1", "string2", "string3");

        //Create a stream of strings and then convert back into list so that we can do the test
        List<String> result = lse.createStrStream("string1", "string2", "string3").collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void toUpperCase() {
        List<String> expected = Arrays.asList("STRING1", "STRING2", "STRING3");

        //convert our list of strings to uppercase and then convert back into list so that we can do the test
        List<String> result = lse.toUpperCase("stRing1", "string2", "StrIng3").collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void filter() {
        List<String> expected = Arrays.asList("string3");

        //filter our strings with patter "1" and then convert the resulted stream back to list
        List<String> result = lse.filter(Stream.of("string1", "string1","string3"), "1")
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void createIntStream() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

        //create an Integer stream and then convert back to list to do comparison
        List<Integer> result = lse.createIntStream(new int[]{1, 2, 3, 4, 5}).boxed()
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void toList() {
        List<String> expected = Arrays.asList("string1", "string2", "string3");

        //convert our stream of strings to a list
        List<String> result = lse.toList(Stream.of("string1", "string2", "string3"));
        assertEquals(expected, result);
    }

    @Test
    public void squareRootIntStream() {
        List<Double> expected = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);

        //create a stream of integers that should return square roots of 1-5 and then convert to list
        List<Double> result = lse.squareRootIntStream(IntStream.of(new int[]{1, 4, 9, 16, 25}))
                .boxed().collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void getOdd() {
        List<Integer> expected = Arrays.asList(1, 3, 5, 7, 9);

        //create a stream of integers from 1-10 and then call getOdd and convert back
        List<Integer> result = lse.getOdd(IntStream.rangeClosed(1, 10)).boxed()
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void getLambdaPrinter() {
        Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
        printer.accept("Message body");
    }

    @Test
    public void printMessages() {
        String[] messages = {"a", "b", "c"};
        lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!"));
    }

    @Test
    public void printOdd() {
        lse.printOdd(lse.createIntStream(0, 10), lse.getLambdaPrinter("odd number:", "!"));
    }

    @Test
    public void flatNestedInt() {
        List<Integer> expected = Arrays.asList(1, 4, 9, 16, 25);
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(4, 5);
        List<List<Integer>> c = Arrays.asList(a, b);

        //use flatNestedInt to square each int and then convert back to list to compare
        List<Integer> result = lse.flatNestedInt(c.stream())
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }
}