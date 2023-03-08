package com.example.demo.draft.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompareColletcions {

    public static void main(String[] args) {
        List<Long> first = new ArrayList<>();
        first.add(5L);
        first.add(2L);
        first.add(1L);
        List<Long> sec = new ArrayList<>();
        sec.add(1L);
        sec.add(2L);
        sec.add(3L);
        sec.add(4L);
        sec.add(5L);

        System.out.println(sec);

        //sec.removeIf(e -> !first.contains(e));
        Iterator<Long> iter = sec.iterator();
        while (iter.hasNext()) {
            if (!first.contains(iter.next())) {
                iter.remove();
            }
        }
        System.out.println(sec);

        List<Long> newOne = new ArrayList<>(sec);


        System.out.println(newOne);

        String msg = ("400") + (" \"" + "this.reason" + "\"");
        System.out.println(msg);

        // Разделить список на части
        var input = Arrays.asList(
                "a", "b", "c", "g", "h", "i", "h", "i", "b", "c",
                "d", "e", "f", "g", "h", "i", "e", "f", "g", "h");
        int partitionSize = input.size() / 10;
        var digits = 1;
        if (partitionSize > digits) {
            var newResult = IntStream.range(0, input.size())
                    .boxed()
                    .collect(Collectors.groupingBy(partition -> (partition / partitionSize), Collectors.mapping(elementIndex -> input.get(elementIndex), Collectors.toList())))
                    .values();
            for (List<String> newList : newResult) {
                System.out.println(newList);
            }
        } else {
            System.out.println(input);
        }





        System.out.println(partitionSize);
        System.out.println(input.size());
        System.out.println(input.size() / 10);
        System.out.println(input.size() / 10 > 1);
        System.out.println(input.size() / 10 < 1);
    }
}
