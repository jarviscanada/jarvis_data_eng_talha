package ca.jrvs.practice.codingChallenge;

/**
 * Given a String which contains only lower and upper case letters, print letter index after each character.
 * For example, the index of a is 1, b is 2, A is 27, and so on.
 * Ex. input: "aBcEe" then output: "a1b28c3e31e5"
 * Time complexity is O(n) as we need to iterate through the string once
 */
public class PrintLetterWithNumber {

    public String printLetterWithNumber(String input) {
        StringBuilder result = new StringBuilder();
        char[] inputArray = input.toCharArray();

        for(char c: inputArray) {
            result.append(c);

            if (c >= 'a' && c <= 'z') {
                result.append(c - 'a' + 1);
            }
            else if (c >= 'A' && c <= 'Z') {
                result.append(c - 'A' + 27);
            }
            else {
                throw new IllegalArgumentException("Invalid input, only upper and lower case letters allowed");
            }
        }

        return result.toString();
    }
}
