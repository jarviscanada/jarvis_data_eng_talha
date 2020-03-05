package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class DuplicateCharactersTest {

    DuplicateCharacters duplicateCharacters = new DuplicateCharacters();

    @Test
    public void duplicateChars() {
        Character[] test1 = {'a', 'c'};
        assertArrayEquals(test1, duplicateCharacters.duplicateChars("A black cat"));

        Character[] test2 = {'s', 't', 'e', 'h', 'i'};
        assertArrayEquals(test2, duplicateCharacters.duplicateChars("This is another test"));
    }
}