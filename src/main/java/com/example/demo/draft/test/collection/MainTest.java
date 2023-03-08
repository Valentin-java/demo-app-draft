package com.example.demo.draft.test.collection;

import com.example.demo.draft.model.FindUnsignedDocumentsResponse;
import com.example.demo.draft.model.RegistryShort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainTest {

    public static void main(String[] args) {
        TargetUser tu = new TargetUser(List.of("test1", "test2"));

        System.out.println(tu.getName());

        var newList = new ArrayList<>(tu.getName());
        newList.add("test3");
        tu.setName(newList);

        System.out.println(tu.getName());

        List<String> test2 = new ArrayList<>();
        test2.add("test");
//        test2.add("test");
//        test2.add("test");

        List<Long> test1 = new ArrayList<>();
        test1.add(1L);
        test1.add(1L);
        test1.add(1L);

        System.out.println(test1.stream().map(e -> e.toString()).collect(Collectors.joining(", ")));
        System.out.println(test2.stream().map(e -> "'"+e+"'").collect(Collectors.joining(", ")));
        System.out.println("**************************************");

        List<RegistryShort> registries = new ArrayList<>();
        registries.add(new RegistryShort().setId(1L).setRegistrySum("10"));
        registries.add(new RegistryShort().setId(2L).setRegistrySum("10"));
        registries.add(new RegistryShort().setId(3L).setRegistrySum("10"));


        BigDecimal totalSum = BigDecimal.ZERO;
        List<Long> listIds = new ArrayList<>();
        var regestryList = registries;
        for (RegistryShort r : regestryList) {
            listIds.add(r.getId());
            totalSum = totalSum.add(new BigDecimal(r.getRegistrySum()));
        }

        var res = new FindUnsignedDocumentsResponse(listIds, String.valueOf(totalSum));

        for (Long e : res.getDocumentIds()) {
            System.out.println(e.toString());
        }

        System.out.println(res.getSum());

    }
}
