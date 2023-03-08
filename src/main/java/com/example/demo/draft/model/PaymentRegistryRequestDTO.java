package com.example.demo.draft.model;

import lombok.Data;

@Data
public class PaymentRegistryRequestDTO {
    private PaymentRegistryDTO paymentRegistry;
    private UserDTO user;
}