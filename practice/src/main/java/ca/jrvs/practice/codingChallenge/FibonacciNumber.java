package ca.jrvs.practice.codingChallenge;

public class FibonacciNumber {

    public int fibRecursive (int num) {
        if (num == 0 || num == 1) {
            return 1;
        }
        else {
            return fibRecursive(num - 1) + fibRecursive( num - 2);
        }
    }

    public int fibDynamin (int num) {
        int[] result = new int[num + 1];

        if (num == 0 || num == 1) {
            return 1;
        }

        result[0]=1;
        result[1]=1;

        for(int i = 2 ; i <= num; i++) {
            result[i] = result[i-1] + result[i-2];
        }

        return result[num];
    }
}
