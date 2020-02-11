package ca.jrvs.practice.codingChallenge;

public class StringToInteger {
    public int usingParsing (String input) {
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public int withoutParsing (String input) {
        input = input.trim();

        if (input.isEmpty()) {
            return 0;
        }
        int sign = 1; int i = 0;

        if (input.charAt(0) == '-' || input.charAt(0) == '+') {
            sign = (input.charAt(0) == '-') ? -1 : 1;
            if (input.length() < 2 || !Character.isDigit(input.charAt(1))) {
                return 0;
            }
            i++;
        }
        int n = 0;

        while (i < input.length()) {
            if (Character.isDigit(input.charAt(i))) {
                int d = input.charAt(i) - '0';
                if (n > (Integer.MAX_VALUE - d) / 10) {
                    //Check for integer overflow
                    n = (sign == -1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                    return n;
                }
                n = n*10 + d;
            }
            else {
                break;
            }
            i++;
        }
        return sign * n;
    }
}
