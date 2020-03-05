package ca.jrvs.practice.codingChallenge;

import java.util.Map;

public class CompareTwoMaps {

    public <K, V> boolean compareTwoMaps (Map<K, V> map1, Map<K, V> map2) {
        //First check if sizes are equal
        if (map1.size() != map2.size()) {
            return false;
        }

        //If they point to same memory location then they must be the same
        if (map1 == map2) {
            return true;
        }

        //Go through each entry in map1 to see if its the same in map2
        for (Map.Entry <K,V> entry: map1.entrySet()) {
            if (map2.containsKey(entry.getKey()) == false) {
                return false;
            }

            if (!map2.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }

        //if we get past the for loop, we know both maps have same key value pairs
        return true;
    }

    public <K, V> boolean compareTwoMapsWithMapApi (Map<K, V> map1, Map<K, V> map2) {
        return map1.equals(map2);
    }
}
