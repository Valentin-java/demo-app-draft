package com.example.demo.draft.model;

import lombok.Data;

@Data
public class Department {

    //id
    private Long id;
    //адрес доп офиса
    private String address;
    //код филиала
    private String branchCode;
    //город
    private String city;
    //код доп офиса
    private String code;
    //полное название доп офиса
    private String name;

}
