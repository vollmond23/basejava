package ru.javaops.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{5, 3, 2, 2, 1, 1, 3, 1, 2, 3}));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5)));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::valueOf).sum();
        return integers.stream()
                .filter(x -> sum % 2 != x % 2)
                .collect(Collectors.toList());
    }

}

