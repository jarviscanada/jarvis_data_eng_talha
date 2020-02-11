package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FindMinMaxInArray {
    public int[] usingForLoop (int[] input) {
        int[] result = new int[2];
        int min = input[0];
        int max = input[0];

        for (int i : input) {
            if (i < min) {
                min = i;
            }
            if (i > max) {
                max = i;
            }
        }
        result[0] = min;
        result[1] = max;

        return result;
    }

    public int[] usingStreams (int[] input) {
        int[] result = new int[2];
        int min = Arrays.stream(input)
                .min()
                .getAsInt();

        int max = Arrays.stream(input)
                .max()
                .getAsInt();

        result[0] = min;
        result[1] = max;

        return result;
    }

    public int[] usingJavaApi (int[] input) {
        int[] result = new int[2];
        //In order to use Collections.min/max we need to convert array to list

        List<Integer> inputList = Arrays.stream(input)
                .boxed()
                .collect(Collectors.toList());

        int min = Collections.min(inputList);
        int max = Collections.max(inputList);

        result[0] = min;
        result[1] = max;

        return result;
    }
}
