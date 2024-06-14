package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Entity
@Data
public class Call extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private String taskType;
    private List<UUID> cid;
    private boolean taskScheduled;
    private String dueDate;
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
}
