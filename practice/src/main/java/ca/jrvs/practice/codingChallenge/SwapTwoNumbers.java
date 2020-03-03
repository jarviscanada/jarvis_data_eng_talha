package ca.jrvs.practice.codingChallenge;

public class SwapTwoNumbers {
    public void usingArithmetic (int[] input, int first, int second) {
        //first = first + second
        input[first] = input[first] + input[second];
        //second = first - second (which is first + second - second)
        //And that results in setting second to the value of first
        input[second] = input[first] - input[second];
        //first = first - second (which is first + second - first)
        //And that results in setting first to the value of second
        input[first] = input[first] - input[second];
    }

    public void usingBitManipulation (int[] input, int first, int second) {
        //first = first ^ second
        input[first] = input[first] ^ input[second];
        //second = first ^ second ^ second (which gives us first as second cancels out)
        input[second] = input[first] ^ input[second];
        //first = first ^ second ^ second (but second is first, so it is really
        //first = first ^ second ^ first) which will give us value of second
        input[first] = input[first] ^ input[second];
    }
}
