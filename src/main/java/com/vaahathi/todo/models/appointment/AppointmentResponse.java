package com.vaahathi.todo.models.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private UUID id;
    private UUID ownerId;
    private UUID pid;
    private List<UUID> cid;
    private String category;
    private String taskType;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private List<String> history;
    private String personName;
    private long phoneNumber;
    private List<String> hierarchy;
    private String status;
    private List<UUID> accessibleBy;
    private UUID assignedTo;
}
