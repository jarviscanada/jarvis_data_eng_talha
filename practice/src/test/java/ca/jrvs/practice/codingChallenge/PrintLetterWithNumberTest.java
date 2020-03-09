package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrintLetterWithNumberTest {

    PrintLetterWithNumber printLetterWithNumber = new PrintLetterWithNumber();

    @Test
    public void printLetterWithNumberTest() {
        assertEquals("a1b2c3e5e5", printLetterWithNumber.printLetterWithNumber("abcee"));
        assertEquals("a1b2c3e5g7i9d4", printLetterWithNumber.printLetterWithNumber("abcegid"));
        assertEquals("A27b2c3e5G33i9D30", printLetterWithNumber.printLetterWithNumber("AbceGiD"));
    }
}