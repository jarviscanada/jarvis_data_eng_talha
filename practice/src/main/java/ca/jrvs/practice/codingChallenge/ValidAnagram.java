package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ValidAnagram {
    public boolean anagramWithSorting (String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] sCharArray = s.toCharArray();
        char[] tCharArray = t.toCharArray();

        Arrays.sort(sCharArray);
        Arrays.sort(tCharArray);

        return Arrays.equals(sCharArray, tCharArray);
    }

    public boolean anagramWithArray (String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] counter = new int[26];

        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a'] ++;
            counter[t.charAt(i) - 'a'] --;
        }

        for (int count : counter) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public boolean anagramWithMaps (String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();

        char[] sCharArray = s.toCharArray();
        char[] tCharArray = t.toCharArray();

        for (char x : sCharArray) {
            if (map.containsKey(x)) {
                map.put(x, map.get(x) + 1);
            }
            else {
                map.put(x, 1);
            }
        }

        for (char x : tCharArray) {
            if (map.containsKey(x) == false) {
                return false;
            }
            int count = map.get(x);

            if (count == 0) {
                return false;
            }
            else if (count == 1) {
                map.remove(x);
            }
            else {
                map.put(x, map.get(x) - 1);
            }
        }

        return map.isEmpty();
    }
}