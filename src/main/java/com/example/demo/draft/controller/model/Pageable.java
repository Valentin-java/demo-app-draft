package com.example.demo.draft.controller.model;

import lombok.Data;

@Data
public class Pageable {
    // c какого нужно искать
    private long offset;

    // ограничение текущее на итемы
    private long itemsLimit;

}
