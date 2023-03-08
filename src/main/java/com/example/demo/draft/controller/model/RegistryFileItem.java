package com.example.demo.draft.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistryFileItem {

    private Long externalId;
    private String name;
    private RegistryFileType type;
}