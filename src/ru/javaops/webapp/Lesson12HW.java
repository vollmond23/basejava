package ru.javaops.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Lesson12HW {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{5, 3, 2, 2, 1, 1, 3, 1, 2, 3}));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5)));
    }

    static int minValue(int[] values) {
        return Integer.parseInt(
                Arrays.stream(values)
                        .distinct()
                        .sorted()
                        .mapToObj(Objects::toString)
                        .collect(Collectors.joining()));
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();
        int sum = integers.stream()
                .mapToInt(Integer::valueOf)
                .peek(x -> {
                    if (x % 2 == 0) {
                        even.add(x);
                    } else {
                        odd.add(x);
                    }
                })
                .sum();
        if (sum % 2 == 0) {
            return odd;
        }
        return even;
    }

}

