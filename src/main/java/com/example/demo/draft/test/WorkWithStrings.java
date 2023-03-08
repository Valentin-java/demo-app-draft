package com.example.demo.draft.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.example.demo.draft.entity.RegistryFileType.REGISTRY;

public class WorkWithStrings {

    public static void main(String[] args) {
         String in = "\"400 : {\"code\":400,\"description\":\"Действие с документом недоступно\"}";
        //String in = null;
        var in2 = Optional.ofNullable(in).orElse("Unknown description");
        var target = in2
                .replaceAll("\"", "")
                .replaceAll("\\}", "")
                .split(":");
        System.out.println(target[target.length-1].trim());



        //String result = target.substring(target.indexOf("description:")+12, target.length()-1);
        //System.out.println(result);
    }


}
