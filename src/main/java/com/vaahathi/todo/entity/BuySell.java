package com.vaahathi.todo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class BuySell extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private String category;
    private UUID ownerId;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String taskType = "buysell";
    private String purpose;
    private boolean dependency;
    private String personName;
    private long phoneNumber;
    private String callNote;
    private List<String> hierarchy;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.INITIATED;
    private Integer priority;

}
