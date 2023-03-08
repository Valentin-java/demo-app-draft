package com.example.demo.draft.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {

    private Long id;
    private String externalId;
    private String name;
    private Long size;
    private String type;
    private String elibId;
    private String base64;
    private String archiveName;
}