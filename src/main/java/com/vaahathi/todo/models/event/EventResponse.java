package com.vaahathi.todo.models.event;

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
public class EventResponse {
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private String history;
    private String category;
    private UUID ownerId;
    private String name;
    private List<String> hierarchy;
    private String status;
    private List<UUID> accessibleBy;
    private UUID assignedTo;
}
