package com.vaahathi.todo.models.mail;

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
public class MailResponse {
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String taskType;
    private String category;
    private UUID ownerId;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;

    private String personName;
    private String eMailId;
    private List<String> hierarchy;
    private List<String> history;
}
