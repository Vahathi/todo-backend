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
public class EventRequest {
    private UUID ownerId;
    private UUID pid;
    private String createdBy;
    private String updatedBy;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;

    private String category;
    private String name;
    private List<String> parentHierarcy;
    private List<String> history;
}
