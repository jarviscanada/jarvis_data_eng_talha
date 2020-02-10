package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackUsingQueuesTest {

    StackUsingQueues<Integer> stackUsingQueues = new StackUsingQueues<>();

    @Test
    public void StackUsingQueuesTest() {
        assertEquals(true, stackUsingQueues.empty());

        stackUsingQueues.push(10);
        stackUsingQueues.push(20);
        stackUsingQueues.push(50);
        stackUsingQueues.push(100);
        stackUsingQueues.push(6000);

        assertEquals(false, stackUsingQueues.empty());
        assertEquals(6000, stackUsingQueues.top().intValue());
        assertEquals(6000, stackUsingQueues.pop().intValue());

        assertEquals(100, stackUsingQueues.top().intValue());
        assertEquals(100, stackUsingQueues.pop().intValue());

        assertEquals(50, stackUsingQueues.top().intValue());
    }
}