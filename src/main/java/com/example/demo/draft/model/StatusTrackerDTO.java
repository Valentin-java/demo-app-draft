package com.example.demo.draft.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatusTrackerDTO {
    private SignatureDTO signature;
    private List<EventGroupDTO> eventGroups;
}
