package com.vaahathi.todo.models.mail;

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
public class MailResponse {
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private int hierarchy;
    private String taskType;
    private String category;
    private UUID ownerId;
    private boolean taskScheduled;
    private String dueDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
    private String  personName;
    private String eMailId;
}
