package ca.jrvs.practice.codingChallenge;

public class FibonacciNumber {

    // Big-O: O(2 ^ n), exponential because of recursion
    public int fibRecursive (int num) {
        if (num <= 1) {
            return num;
        }
        else {
            return fibRecursive(num - 1) + fibRecursive( num - 2);
        }
    }

    // Big-O: O(n) as we only have one for loop iteration
    public int fibDynamic (int num) {
        int[] result = new int[num + 1];

        if (num <= 1) {
            return num;
        }

        result[0]=0;
        result[1]=1;

        for(int i = 2 ; i <= num; i++) {
            result[i] = result[i-1] + result[i-2];
        }

        return result[num];
    }
}
