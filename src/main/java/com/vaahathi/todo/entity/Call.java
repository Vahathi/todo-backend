package com.vaahathi.todo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Call extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType = "call";
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private String category;
    private boolean dependency;
    private UUID ownerId;
    private List<Long> accessibleBy;
    private int assignedTo;
    private String personName;
    private long phoneNumber;
    private String callNote;
    private List<String> hierarchy;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.INITIATED;
    private Integer priority;
}
