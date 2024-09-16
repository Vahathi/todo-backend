package com.vaahathi.todo.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@MappedSuperclass
public class CommonBaseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private UUID ownerId;
    private String category;
    private boolean isScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private String personName;
    private long phoneNumber;
    private String history;
    private List<String> hierarchy;
    private String status = "initiated";
    private Integer priority;
    private List<UUID> accessibleBy;
    private UUID assignedTo;
}
