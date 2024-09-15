package com.vaahathi.todo.models.call;

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
public class CallRequest {
    private UUID ownerId;
    private UUID pid;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private String category;
    private boolean dependency;
    private List<UUID> accessibleBy;
    private UUID assignedTo;
    private String personName;
    private long phoneNumber;
    private List<String> parentHierarcy;
    private String status = "initiated";

}
