package com.vaahathi.todo.models.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private UUID ownerId;
    private UUID pid;
    private String category;
    private String hierarchy;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private List<String> history;
    private String paymentPurpose;
    private int amount;
    private String arrearsOrPenalty;
    private int arrearsOrPenaltyAmount;
    private LocalDateTime billGeneratedDate;
    private int repeatDuration;
    private String repeatDurationType;
    private int paidStatus;
    private String paidOn;
    private String message;
    private List<String> parentHierarcy;
    private String status = "initiated";
    private List<UUID> accessibleBy;
    private UUID assignedTo;

}
