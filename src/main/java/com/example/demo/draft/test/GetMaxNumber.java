package com.example.demo.draft.test;

import com.example.demo.draft.model.Registry;
import com.example.demo.draft.model.RegistryDigitsTest;

import java.util.Comparator;
import java.util.List;

public class GetMaxNumber {

    public static void main(String[] args) {
/**
 * Берем максимальное число номера реестра, если привысит 99999 то начинаем с 1
 */
        var reg1 = new RegistryDigitsTest(1L, 10L);
        var reg2 = new RegistryDigitsTest(2L, 99998L);
        var reg3 = new RegistryDigitsTest(3L, 1000L);

        List<RegistryDigitsTest> testDigits = List.of(reg1, reg2, reg3);


        Long registryNumber = testDigits
                .stream()
                .max(Comparator.comparing(RegistryDigitsTest::getId))
                .map(RegistryDigitsTest::getRegistryNumber)
                .filter(number -> number < 99999L)
                .orElse(0L) + 1;

        System.out.println(registryNumber);
    }
}
