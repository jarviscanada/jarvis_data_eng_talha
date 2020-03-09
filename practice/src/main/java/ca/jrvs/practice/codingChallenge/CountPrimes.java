package ca.jrvs.practice.codingChallenge;

public class CountPrimes {
    public int countPrime(int n) {
        if (n < 2) {
            return 0;
        }

        int[] primeCheck = new int[n];
        for (int i = 2; i < n; i++) {
            //Value of 1 for prime and 0 for non prime, first set them all to 1
            primeCheck[i] = 1;
        }

        int squareRoot = (int) Math.sqrt(n);
        for (int p = 2; p <= squareRoot; p++) {
            if (primeCheck[p] == 1) {
                //Correctly set non primes to 0
                for (int i = p * p; i < n; i += p) {
                    primeCheck[i] = 0;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (primeCheck[i] == 1) {
                count++;
            }
        }
        return count;
    }
}
