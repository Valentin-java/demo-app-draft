package com.example.demo.draft.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class NFODepartmentDTO {
    private Long id;
    private String shortName;
    private String aliasName;
    private Integer branchId;
    private String branchCode;
    private String parentId;
    private String typeCode;
    private String city;
    private String departmentAddress;
    private String email;
    private Boolean isCorpVisible;
    private Integer sortOrder;
    private LocalDate startDate;
    private LocalDate endDate;
    private String timezoneDelta;
}