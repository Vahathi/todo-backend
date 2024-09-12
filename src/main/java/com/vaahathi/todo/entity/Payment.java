package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType = "payment";
    private String category;
    private UUID ownerId;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;

    private String paymentPurpose;
    private int amount;
    private String arrearsOrPenalty;
    private int arrearsOrPenaltyAmount;
    private LocalDateTime billGeneratedDate;
    private int repeatDuration;
    private String repeatDurationType;
    private int paidStatus;
    private String paidOn;
    private String paymentHistory;
    private String message;
    private List<String> hierarchy;
}
