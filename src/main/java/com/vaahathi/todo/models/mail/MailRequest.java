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
public class MailRequest {
    private UUID ownerId;
    private UUID pid;
    private int hierarchy;
    private String category;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;

    private String personName;
    private String eMailId;
    private List<String> parentHierarcy;
    private List<String> history;
}
