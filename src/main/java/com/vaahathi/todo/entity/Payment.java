package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment extends CommonBaseEntity {
    private int amount;
    private String arrearsOrPenalty;
    private int arrearsOrPenaltyAmount;
    private LocalDateTime billGeneratedDate;
    private int repeatDuration;
    private String repeatDurationType;
    private int paidStatus;
    private String paidOn;
    private String message;
    private String taskType = "payment";

}
