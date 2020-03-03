package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringContainsDigitsTest {
    StringContainsDigits stringContainsDigits = new StringContainsDigits();

    @Test
    public void StringContainsDigits() {
        assertEquals(true, stringContainsDigits.usingASCII("1234"));
        assertEquals(true, stringContainsDigits.usingASCII("-10"));
        assertEquals(false, stringContainsDigits.usingASCII("123,000"));
        assertEquals(false, stringContainsDigits.usingASCII("-10f"));

        assertEquals(true, stringContainsDigits.usingJavaApi("1234"));
        assertEquals(true, stringContainsDigits.usingJavaApi("-10"));
        assertEquals(false, stringContainsDigits.usingJavaApi("123,000"));
        assertEquals(false, stringContainsDigits.usingJavaApi("-10f"));

        assertEquals(true, stringContainsDigits.usingRegex("1234"));
        assertEquals(true, stringContainsDigits.usingRegex("-10"));
        assertEquals(false, stringContainsDigits.usingRegex("123,000"));
        assertEquals(false, stringContainsDigits.usingRegex("-10f"));
    }
}