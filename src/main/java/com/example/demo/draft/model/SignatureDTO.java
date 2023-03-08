package com.example.demo.draft.model;

import lombok.Data;

import java.util.List;

@Data
public class SignatureDTO {
    private List<String> signed;
    private List<String> missed;
}