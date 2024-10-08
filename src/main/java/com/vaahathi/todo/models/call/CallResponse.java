package com.vaahathi.todo.models.call;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallResponse {
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
