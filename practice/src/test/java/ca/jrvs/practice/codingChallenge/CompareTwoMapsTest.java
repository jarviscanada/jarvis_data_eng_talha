package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompareTwoMapsTest {

    CompareTwoMaps compareTwoMaps = new CompareTwoMaps();

    private Map<Integer, Integer> map1 = new HashMap<>();
    private Map<Integer, Integer> map2 = new HashMap<>();
    private Map<Integer, Integer> map3 = new HashMap<>();
    private Map<Integer, Integer> map4 = new HashMap<>();
    private Map<Integer, Integer> map5 = new HashMap<>();

    @Test
    public void compareTwoMaps() {
        map1.put(1, 1);
        map1.put(5, 10);
        map1.put(20, 20);

        map2.put(1, 1);
        map2.put(5, 10);
        map2.put(20, 20);

        map3.put(1, 1);

        map4.put(1, 10);
        map4.put(5, 15);
        map4.put(20, 2);

        map5 = map1;

        assertEquals(compareTwoMaps.compareTwoMaps(map1, map2), true);
        assertEquals(compareTwoMaps.compareTwoMaps(map1, map3), false);
        assertEquals(compareTwoMaps.compareTwoMaps(map1, map4), false);
        assertEquals(compareTwoMaps.compareTwoMaps(map1, map5), true);
    }

    @Test
    public void compareTwoMapsWithMapApi() {
        map1.put(1, 1);
        map1.put(5, 10);
        map1.put(20, 20);

        map2.put(1, 1);
        map2.put(5, 10);
        map2.put(20, 20);

        map3.put(1, 1);

        map4.put(1, 10);
        map4.put(5, 15);
        map4.put(20, 2);

        map5 = map1;

        assertEquals(compareTwoMaps.compareTwoMaps(map1, map2), true);
        assertEquals(compareTwoMaps.compareTwoMaps(map1, map3), false);
        assertEquals(compareTwoMaps.compareTwoMaps(map1, map4), false);
        assertEquals(compareTwoMaps.compareTwoMaps(map1, map5), true);
    }
}