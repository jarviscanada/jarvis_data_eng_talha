package ca.jrvs.practice.codingChallenge;

public class ValidPalindrome {

    public boolean isPalindrome (String s) {
        if (s.length() == 0) {
            return false;
        }

        int i = 0;
        int j = s.length() - 1;

        while (i < j) {
            if (Character.isLetterOrDigit(s.charAt(i)) == false) {
                i++;
            }
            else if (Character.isLetterOrDigit(s.charAt(j)) == false) {
                j--;
            }
            else {
                //both i and j are letters or digits
                if (Character.toLowerCase(s.charAt(i)) !=
                        Character.toLowerCase(s.charAt(j))) {
                    return false;
                }
                i++;
                j--;
            }
        }
        return true;
    }
}
