package com.example.demo.draft.model;

import lombok.Data;

@Data
public class DepartmentDTO {
    private Long id;
    private String code;
    private String branchCode;
    private String name;
    private String city;
    private String address;
    private LocalDateDTO startDate;
    private LocalDateDTO endDate;
    private String timezoneDelta;
    private Integer sortOrder;
}
