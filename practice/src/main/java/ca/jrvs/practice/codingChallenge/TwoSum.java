package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ticket: https://www.notion.so/Two-Sum-795505a1106845a6958706c19cd22295
 */
public class TwoSum {

    /**
     * Big-O: O(n^2)
     * Justification: Nested for loops, both going through the array leads to O(n^2)
     * @param nums - input array of ints
     * @param target - target we must compute (see if any two ints in the array add to this number)
     * @return an array of ints containing the indices of the two ints that add to the target (first pair)
     *         if no pair is found, an array containing -1, -1 is returned
     */
    public int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1,-1};
        // or we can throw new IllegalArgumentException("No two nums in the array add up to target");
    }

    /**
     * Big-O: O(n)
     * Justification: Array is sorted and we only go through it once, so it is O(n)
     * @param nums - input array of ints
     * @param target - target we must compute (see if any two ints in the array add to this number)
     * @return an array of ints containing the indices of the two ints that add to the target (first pair)
     *         if no pair is found, an array containing -1, -1 is returned
     */
    public int[] twoSumSorted(int[] nums, int target) {
        //Sort the array first
        Arrays.sort(nums);

        int first = 0;
        int last = nums.length - 1;

        while (first < last) {
            int sum = nums[first] + nums[last];

            if (sum == target) {
                return new int[]{first, last};
            }
            else if (sum < target) {
                first++;
            }
            else {
                last--;
            }
        }
        return new int[] {-1,-1};
        //throw new IllegalArgumentException("No two nums in the array add up to target");
    }

    /**
     * Big-O: O(n)
     * Justification: Map search operations run in O(1) time and we only loop through the array once
     * @param nums - input array of ints
     * @param target - target we must compute (see if any two ints in the array add to this number)
     * @return an array of ints containing the indices of the two ints that add to the target (first pair)
     *         if no pair is found, an array containing -1, -1 is returned
     */
    public int[] twoSumUsingMap(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int check = target - nums[i];
            if (map.containsKey(check)) {
                return new int[]{map.get(check), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[] {-1,-1};
        //throw new IllegalArgumentException("No two nums in the array add up to target");
    }
}
