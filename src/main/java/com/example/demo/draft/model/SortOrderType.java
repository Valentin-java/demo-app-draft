package com.example.demo.draft.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SortOrderType {
    //по возрастанию
    ASC("asc"),
    //по убыванию
    DESC("desc");

    private String value;

    SortOrderType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static SortOrderType fromValue(String value) {
        for (SortOrderType b : SortOrderType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

