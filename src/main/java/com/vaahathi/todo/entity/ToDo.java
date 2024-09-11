package com.vaahathi.todo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class ToDo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType = "todo";
    private boolean TaskScheduled;
    private LocalDateTime appointmentDate;
    private String category;
    private UUID ownerId;
    private boolean Urgent;
    private boolean Important;
    private String purpose;
    private boolean dependency;
    private long phoneNumber;
    private String toDoHistory;
    private String note;
    private List<String> hierarchy;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.INITIATED;
    private Integer priority;
}
