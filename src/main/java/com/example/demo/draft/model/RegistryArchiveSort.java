package com.example.demo.draft.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegistryArchiveSort {

    @JsonProperty("by")
    private RegistrySortFields fieldSort;

    @JsonProperty("type")
    private SortOrderType type;

}
