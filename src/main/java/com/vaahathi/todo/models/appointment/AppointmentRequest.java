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
public class AppointmentRequest {
    private UUID ownerId;
    private UUID pid;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private String personName;
    private String history;
    private long phoneNumber;
    private String category;
    private List<String> parentHierarcy;
    private String status = "initiated";
    private List<UUID> accessibleBy;
    private UUID assignedTo;

}
