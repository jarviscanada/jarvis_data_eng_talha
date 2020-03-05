package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueueUsingStacksTest {

    QueueUsingStacks<Integer> queueUsingStacks = new QueueUsingStacks<>();

    @Test
    public void queueUsingStacksTest() {
        assertEquals(true, queueUsingStacks.empty());

        queueUsingStacks.push(10);
        queueUsingStacks.push(20);
        queueUsingStacks.push(50);
        queueUsingStacks.push(100);
        queueUsingStacks.push(6000);

        assertEquals(false, queueUsingStacks.empty());

        assertEquals(10, queueUsingStacks.peek().intValue());
        assertEquals(10, queueUsingStacks.pop().intValue());

        queueUsingStacks.push(3);

        assertEquals(20, queueUsingStacks.peek().intValue());
        assertEquals(20, queueUsingStacks.pop().intValue());

        assertEquals(50, queueUsingStacks.peek().intValue());
    }
}