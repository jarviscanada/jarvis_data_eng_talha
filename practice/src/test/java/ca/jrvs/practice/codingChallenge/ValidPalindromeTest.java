package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeTest {
    ValidPalindrome validPalindrome = new ValidPalindrome();

    @Test
    public void ValidPalindromeTest() {
        assertEquals(validPalindrome.isPalindrome("A man, a plan, a canal: Panama"), true);
        assertEquals(validPalindrome.isPalindrome("Was it a cat I saw?"), true);
        assertEquals(validPalindrome.isPalindrome("kayak"), true);
        assertEquals(validPalindrome.isPalindrome("Kayak"), true);

        assertEquals(validPalindrome.isPalindrome("race a car"), false);
        assertEquals(validPalindrome.isPalindrome("Not a palindrome"), false);
    }
}