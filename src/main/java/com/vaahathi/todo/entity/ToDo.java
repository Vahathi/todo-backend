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
public class ToDo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType="todo";
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
}
