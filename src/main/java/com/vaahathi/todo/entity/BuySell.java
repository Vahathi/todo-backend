package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Entity
@Data
public class BuySell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType;
    private String category;
    private UUID ownerId;
    private boolean taskScheduled;
    private String dueDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
    private String personName;
    private long phoneNumber;
    private String callNote;

}
