package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringToIntegerTest {
    StringToInteger stringToInteger = new StringToInteger();

    @Test
    public void StringToIntegerTest() throws Exception {
        assertEquals(10, stringToInteger.usingParsing("10"));
        assertEquals(-10, stringToInteger.usingParsing("-10"));
        assertEquals(0, stringToInteger.usingParsing("-10f"));
        assertEquals(0, stringToInteger.usingParsing("-10 with words"));
        assertEquals(0, stringToInteger.usingParsing("     -10"));

        assertEquals(10, stringToInteger.withoutParsing("10"));
        assertEquals(-10, stringToInteger.withoutParsing("-10"));
        assertEquals(-10, stringToInteger.withoutParsing("-10f"));
        assertEquals(-10, stringToInteger.withoutParsing("-10 with words"));
        assertEquals(-10, stringToInteger.withoutParsing("     -10"));
    }
}