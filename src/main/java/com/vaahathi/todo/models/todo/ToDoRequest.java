package com.vaahathi.todo.models.todo;

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
public class ToDoRequest {
    private String taskType;
    private boolean TaskScheduled;
    private String appointmentDate;
    private String category;
    private boolean Urgent;
    private boolean Important;
    private String purpose;
    private boolean dependency;
    private String subject;
    private long phoneNumber;
    private String toDoHistory;
    private String note;
}
