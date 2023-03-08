package com.example.demo.draft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistryArchiveFilter {

    private List<Long> clientIds;
    private LocalDate registryDateFrom;
    private LocalDate registryDateTo;
    private String registryNumber;
}
