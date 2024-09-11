package com.vaahathi.todo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String personName;
    private String nickName;
    private long phoneNumber;
    private long altPhoneNumber;
    private String personalEMail;
    private String officeEmail;
    private String webpage;
    private String taskType = "contact";
    private String category;
    private UUID ownerId;
    private String note;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
    private List<String> hierarchy;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.INITIATED;
    private Integer priority;
}
