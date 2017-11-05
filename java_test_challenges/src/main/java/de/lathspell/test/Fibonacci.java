package de.lathspell.test;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static List<Integer> fibIterative(int maxIterations) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        for (int i = 2; i <= maxIterations; i++) {
            list.add(list.get(i - 2) + list.get(i - 1));
        }
        list.remove(0);
        return list;
    }

    public static List<Integer> fibRecursive(int maxIterations) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);

        fibRecursiveLoop(list, 2, maxIterations);

        list.remove(0);
        return list;
    }

    private static List<Integer> fibRecursiveLoop(List<Integer> list, int i, int maxIterations) {
        list.add(list.get(i - 2) + list.get(i - 1));
        if (i < maxIterations) {
            fibRecursiveLoop(list, i + 1, maxIterations);
        }
        return list;
    }

}
