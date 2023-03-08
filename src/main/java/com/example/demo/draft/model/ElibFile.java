package com.example.demo.draft.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ElibFile {
    private String fileName;
    private String base64file;
}
