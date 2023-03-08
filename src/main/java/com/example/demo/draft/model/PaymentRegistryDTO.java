package com.example.demo.draft.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class PaymentRegistryDTO {
    private Long id;
    private Long icbClientId;
    private String icbClientCode;
    private String icbClientName;
    private Long salaryContractId;
    private String salaryContractNumber;
    private String registryNumber;
    private LocalDateTime registryDate;
    private String paymentOrderId;
    private String paymentOrderNumber;
    private LocalDateTime paymentOrderDate;
    private String registrySum;
    private String responsibleEmail;
    private Long userId;
    private List<ActionDTO> actions;
    private StatusShortDTO status;
    private List<FileDTO> files;
    private StatusTrackerDTO statusTracker;
    private Boolean confirmationFree;
    private String clientBisBranchId;
    private String clientBisId;
}