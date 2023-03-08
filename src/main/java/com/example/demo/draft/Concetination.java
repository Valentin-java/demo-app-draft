package com.example.demo.draft;

import org.apache.tomcat.util.buf.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Concetination {

    public static void main(String[] args) {

        List<Long> values = new ArrayList<Long>(){};
        values.add(1L);
        values.add(2L);
        values.add(3L);
        values.add(4L);
        var result = values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        System.out.println(result);

        System.out.printf(LocalDate.now().toString());
    }
}
