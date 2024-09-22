package com.vaahathi.todo.models.buysell;

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
public class BuySellRequest {
    private UUID ownerId;
    private UUID pid;
    private String category;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private List<String> history;
    private String personName;
    private long phoneNumber;
    private List<String> parentHierarcy;
    private String status = "initiated";
    private List<UUID> accessibleBy;
    private UUID assignedTo;
}
