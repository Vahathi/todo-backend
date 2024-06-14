package com.vaahathi.todo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private String taskType;
    private boolean taskScheduled;
    private String dueDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
    private UUID pid;
    private List<UUID> cid;
    private boolean isParent;
    private String category;
    private UUID ownerId;
    private String name;
}
