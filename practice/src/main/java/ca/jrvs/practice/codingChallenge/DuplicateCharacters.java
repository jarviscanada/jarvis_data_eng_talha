package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Finding the duplicated characters in a string.
 * Eg. input: "A black cat" then output: ['a', 'c']
 * Time complexity is O(n)
 */
public class DuplicateCharacters {
    public Character[] duplicateChars (String input) {
        HashMap<Character, Integer> map = new HashMap<>();
        char[] inputArray = input.toCharArray();

        for (char c: inputArray) {
            if (Character.isAlphabetic(c)) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }

        List<Character> resultList = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                resultList.add(entry.getKey());
            }
        }

        Character[] resultArray = resultList.toArray(new Character[resultList.size()]);
        return resultArray;
    }
}
