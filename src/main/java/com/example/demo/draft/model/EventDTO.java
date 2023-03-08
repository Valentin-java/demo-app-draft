package com.example.demo.draft.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private LocalDateTime dateTime;
    private String actorName;
    private Long actorId;
    private String name;
    private String description;
}