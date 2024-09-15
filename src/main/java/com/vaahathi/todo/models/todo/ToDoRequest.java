package com.vaahathi.todo.models.todo;

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
public class ToDoRequest {
    private UUID ownerId;
    private UUID pid;
    private boolean TaskScheduled;
    private LocalDateTime appointmentDate;
    private String category;
    private boolean Urgent;
    private boolean Important;
    private String purpose;

    private long phoneNumber;
    private List<String> parentHierarcy;
    private String status = "initiated";
    private List<UUID> accessibleBy;
    private UUID assignedTo;
}
