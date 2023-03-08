package com.example.demo.draft.model.enums;

public enum DocumentStatus {
    DRAFT(91),
    UNSIGNED(11),
    SIGNED(93),
    REJECTED(31),
    ACCEPTED(19),
    CANCELLATION_REQUESTED(18),
    CANCELED(14),
    PROCESSING(15),
    BUDGET_APPROVAL(81),
    WAITING_FOR_CONFIRMATION(71),
    WAITING_FOR_BUDGET_LINE(82),
    WAITING_FOR_EXECUTION_DATE(25),
    POSTPONED(20),
    ERROR(13),
    SENT(16),
    PASSED_TO_ANTI_FRAUD_CHECK(30),
    PASSED_TO_FIN_MONITORING(32),
    DONE(12),
    PARTIALLY_DISTRIBUTED(40);

    private int value;

    DocumentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DocumentStatus fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        throw new RuntimeException(String.format("Unknown document status: %d", value));
    }
}
