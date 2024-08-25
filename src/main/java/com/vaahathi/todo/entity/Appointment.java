package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Entity
@Data

public class Appointment extends BaseEntity{
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private UUID id;
 private UUID ownerId;
 private String category;
 private UUID pid;
 private List<UUID> cid;
 private String taskType="appointment";
 private boolean taskScheduled;
 private LocalDateTime scheduledDate;
 private boolean isUrgent;
 private boolean isImportant;
 private String purpose;
 private boolean dependency;
 private String personName;
 private long phoneNumber;
}