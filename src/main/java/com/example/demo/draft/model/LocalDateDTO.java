package com.example.demo.draft.model;

import lombok.Data;

@Data
public class LocalDateDTO {
    private Integer year;
    private Integer month;
    private Integer dayOfMonth;

    public LocalDateDTO() {
    }

    public LocalDateDTO(Integer year, Integer month, Integer dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }
}
