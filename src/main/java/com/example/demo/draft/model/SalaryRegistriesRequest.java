package com.example.demo.draft.model;

import com.example.demo.draft.controller.model.Pageable;
import lombok.Data;

@Data
public class SalaryRegistriesRequest {

    private RegistryArchiveFilter filter;

    private Sort order;

    private Pageable pageable;

}
