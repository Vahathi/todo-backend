package com.vaahathi.todo.models.todo;

import com.vaahathi.todo.entity.Status;
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
    private boolean dependency;
    private long phoneNumber;
    private String toDoHistory;
    private String note;
    private List<String> parentHierarcy;
    private Status status = Status.INITIATED;
}
