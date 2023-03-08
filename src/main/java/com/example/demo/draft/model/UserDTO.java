package com.example.demo.draft.model;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String email;
}