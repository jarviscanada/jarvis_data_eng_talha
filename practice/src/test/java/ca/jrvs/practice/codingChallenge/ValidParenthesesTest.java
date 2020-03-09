package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParenthesesTest {
    ValidParentheses validParentheses = new ValidParentheses();

    @Test
    public void parenthesesTest() {
        assertEquals(validParentheses.parenthesesWithMap("()"), true);
        assertEquals(validParentheses.parenthesesWithMap("()[]{}"), true);
        assertEquals(validParentheses.parenthesesWithMap("{[]}"), true);
        assertEquals(validParentheses.parenthesesWithMap("{[([[(())]])]}"), true);

        assertEquals(validParentheses.parenthesesWithMap("(]"), false);
        assertEquals(validParentheses.parenthesesWithMap("([)]"), false);
    }
}