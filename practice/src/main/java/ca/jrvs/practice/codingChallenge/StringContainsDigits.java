package ca.jrvs.practice.codingChallenge;

public class StringContainsDigits {
    public boolean usingASCII(String input) {
        for (char c : input.toCharArray()) {
            boolean signCheck = false;
            if (c == '-' && signCheck == false) {
                signCheck = true;
                continue;
            }
            if (c < 48 || c > 57) {
                return false;
            }
        }
        return true;
    }

    public boolean usingJavaApi(String input) {
        try {
            Integer.valueOf(input);
            //If method did not throw an exception, we can return true
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean usingRegex(String input) {
        return input.matches("^-?[0-9]*$");
    }
}
