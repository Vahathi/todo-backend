package com.vaahathi.todo.models.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String message;
    private UUID id;
    private String taskType;
    private String category;
    private UUID ownerId;
    private String hierarchy;
    private boolean taskScheduled;
    private String dueDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
    private String  paymentPurpose;
    private int amount;
    private String arrearsOrPenalty;
    private int arrearsOrPenaltyAmount;
    private String billGeneratedDate;
    private int repeatDuration;
    private String repeatDurationType;
    private int paidStatus;
    private String paidOn;
    private String paymentHistory;
    public PaymentResponse(String message){
        this.message = message;
    }
}
