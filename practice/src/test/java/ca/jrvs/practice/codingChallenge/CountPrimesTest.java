package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountPrimesTest {
    CountPrimes countPrimes = new CountPrimes();

    @Test
    public void CountPrimes() {
        assertEquals(0, countPrimes.countPrime(1));
        assertEquals(0, countPrimes.countPrime(2));
        assertEquals(2, countPrimes.countPrime(5));
        assertEquals(4, countPrimes.countPrime(10));
        assertEquals(8, countPrimes.countPrime(20));
    }
}