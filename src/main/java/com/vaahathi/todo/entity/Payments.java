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
public class Payments extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType;
    private String category;
    private UUID ownerId;
    private String hierarchy;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
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
}
