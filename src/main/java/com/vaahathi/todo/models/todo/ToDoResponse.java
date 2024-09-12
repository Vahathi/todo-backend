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
public class ToDoResponse {
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType;
    private boolean TaskScheduled;
    private LocalDateTime appointmentDate;
    private String category;
    private UUID ownerId;
    private boolean Urgent;
    private boolean Important;
    private String purpose;

    private long phoneNumber;
    private String toDoHistory;
    private List<String> history;
    private List<String> hierarchy;
}
