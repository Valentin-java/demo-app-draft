package com.example.demo.draft.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestExcResponse {

    Integer code;

    String description;
}
