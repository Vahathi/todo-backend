package com.vaahathi.todo.models.buysell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuySellRequest {
    private String taskType;
    private String category;
    private boolean taskScheduled;
    private String dueDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
    private String personName;
    private long phoneNumber;
    private String callNote;
}
