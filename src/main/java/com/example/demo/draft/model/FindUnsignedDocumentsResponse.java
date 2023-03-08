package com.example.demo.draft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUnsignedDocumentsResponse {
    List<Long> documentIds;
    String sum;
}
