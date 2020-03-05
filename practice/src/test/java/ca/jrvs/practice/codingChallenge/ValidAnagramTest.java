package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidAnagramTest {

    ValidAnagram validAnagram = new ValidAnagram();

    @Test
    public void anagramTest() {
        assertEquals(validAnagram.anagramWithSorting("anagram", "nagaram"), true);
        assertEquals(validAnagram.anagramWithArray("anagram", "nagaram"), true);
        assertEquals(validAnagram.anagramWithMaps("anagram", "nagaram"), true);

        assertEquals(validAnagram.anagramWithSorting("schoolmaster", "theclassroom"), true);
        assertEquals(validAnagram.anagramWithArray("schoolmaster", "theclassroom"), true);
        assertEquals(validAnagram.anagramWithMaps("schoolmaster", "theclassroom"), true);

        assertEquals(validAnagram.anagramWithSorting("rat", "car"), false);
        assertEquals(validAnagram.anagramWithArray("rat", "car"), false);
        assertEquals(validAnagram.anagramWithMaps("rat", "car"), false);

        assertEquals(validAnagram.anagramWithSorting("rat", "rata"), false);
        assertEquals(validAnagram.anagramWithArray("rat", "rata"), false);
        assertEquals(validAnagram.anagramWithMaps("rat", "rata"), false);
    }
}