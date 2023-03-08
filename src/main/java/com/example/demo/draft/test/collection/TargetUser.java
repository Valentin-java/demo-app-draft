package com.example.demo.draft.test.collection;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class TargetUser {

    List<String> name;
}
