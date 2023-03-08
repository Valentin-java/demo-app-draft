package com.example.demo.draft.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventGroupDTO {
    private String name;
    private String code;
    private List<EventDTO> events;
    private String currentState;

    public EventGroupDTO() {
        this.events = new ArrayList<>();
    }
}